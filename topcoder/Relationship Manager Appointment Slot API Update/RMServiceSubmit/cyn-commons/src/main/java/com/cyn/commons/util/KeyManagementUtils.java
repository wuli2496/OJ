package com.cyn.commons.util;

import static com.cyn.commons.exception.ErrorCode.KEY_ERR000001;
import static com.cyn.commons.security.InternalSecretUtil.encryptAppGuid;
import static com.cyn.commons.security.SecurityHeaderConstants.X_AUTHORIZATION_HEADER;

import com.cyn.commons.api.GenericResponse;
import com.cyn.commons.exception.BaseServiceException;
import com.cyn.commons.security.AppGuid;
import com.cyn.commons.security.PrivateKeyResponse;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/** Utility to interact with AEKM Server and fetching the keys */
@Component
public class KeyManagementUtils {

  // The response type for AEKM response
  private static final ParameterizedTypeReference<GenericResponse<PrivateKeyResponse>>
      PRIVATE_KEY_RESP_TYPE = new ParameterizedTypeReference<>() {};
  // The initial delay for private key cleanup thread
  private static final int INITIAL_DELAY = 0;
  // The pool size for private key cleanup thread
  private static final int CLEANUP_THREAD_POOL_SIZE = 1;
  // Algorithm for loading private keys of Applications
  private static final String RSA_ALGORITHM = "RSA";

  /** Get private key url from aekm server * */
  private static final String GET_PRIVATE_KEY_URL = "http://%s/v1/security/service/aekm/privatekey";

  // The internal cache for private keys
  private final Map<AppGuid, PrivateKeyResponse> privateKeys = new ConcurrentHashMap<>();
  // The endpoint/service name of aekm service
  private final String aekmServer;
  // The interval for running cleanup operation
  private final int cleanUpInterval;
  // A rest template for communicating with AEKM Service
  private final RestTemplate restTemplate = new RestTemplate();

  /**
   * Consturctor to be called by Spring
   *
   * @param aekmServer aekm service/host mame
   * @param cleanUpInterval the interval for cleaning up keys from cache
   */
  public KeyManagementUtils(
      @Value("${aekm.server.name}") String aekmServer,
      @Value("${key.cleanup.interval:1}") int cleanUpInterval) {
    this.aekmServer = aekmServer;
    this.cleanUpInterval = cleanUpInterval;
  }

  /** Service initializer that starts the cleanup thread */
  @PostConstruct
  public void init() {
    ScheduledExecutorService scheduledExecutorService =
        Executors.newScheduledThreadPool(CLEANUP_THREAD_POOL_SIZE);
    scheduledExecutorService.scheduleWithFixedDelay(
        this::cleanUpKeys, INITIAL_DELAY, cleanUpInterval, TimeUnit.MINUTES);
  }

  /**
   * This method will fetch the private key from AEKM service via Istio sidecar proxy of Kubernetes
   * Istio service mesh. Https, retry and timeouts will be addressed by sidecar proxy.
   *
   * @param appGuid the unique app guid
   */
  public void fetchPrivateKey(AppGuid appGuid) {
    try {
      String authorizationKey = encryptAppGuid(appGuid);
      HttpHeaders headers = new HttpHeaders();
      headers.set(X_AUTHORIZATION_HEADER, authorizationKey);

      HttpEntity<String> entity = new HttpEntity<>(null, headers);

      GenericResponse<PrivateKeyResponse> response =
          restTemplate
              .exchange(
                  String.format(GET_PRIVATE_KEY_URL, aekmServer),
                  HttpMethod.GET,
                  entity,
                  PRIVATE_KEY_RESP_TYPE)
              .getBody();

      if (response == null) {
        throw new BaseServiceException(KEY_ERR000001.toString(), Collections.emptyList());
      }
      PrivateKeyResponse privateKeyResponse = response.getBody();

      privateKeys.put(appGuid, privateKeyResponse);
    } catch (Exception e) {
      throw new BaseServiceException(KEY_ERR000001.toString(), Collections.emptyList(), e);
    }
  }

  /**
   * A helper method for converting base64 private keys into Java PrivateKey implementation
   *
   * @param key64 the base64 encoded private key
   * @return PrivateKey as RSA implementation
   * @throws GeneralSecurityException if crypto issue occurs during conversation of base64 key
   */
  private PrivateKey loadPrivateKey(String key64) throws GeneralSecurityException {
    byte[] clear = Base64.getDecoder().decode(key64);
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(clear);
    KeyFactory fact = KeyFactory.getInstance(RSA_ALGORITHM);
    PrivateKey priv = fact.generatePrivate(keySpec);
    Arrays.fill(clear, (byte) 0);
    return priv;
  }

  /** The helper method responsible for cleaning up expired keys */
  private void cleanUpKeys() {
    Iterator<Map.Entry<AppGuid, PrivateKeyResponse>> entryIterator =
        privateKeys.entrySet().iterator();
    Map.Entry<AppGuid, PrivateKeyResponse> entry;
    while (entryIterator.hasNext()) {
      entry = entryIterator.next();
      PrivateKeyResponse privateKeyResponse = entry.getValue();
      if (LocalDateTime.parse(privateKeyResponse.getAppKey().getValidTo())
          .isBefore(LocalDateTime.now())) {
        entryIterator.remove();
      }
    }
  }

  /**
   * A publicly available interface for getting private keys for a given AppGuid The consumers are
   * abstracted from internal caching/reloading mechanisms
   *
   * @param appGuid the app guid for returning private keys
   * @return Java PrivateKey RSA implementation
   * @throws GeneralSecurityException if crypto issue occurs during conversation of aekm key
   */
  public PrivateKey getPrivateKey(AppGuid appGuid) throws GeneralSecurityException {
    if (privateKeys.get(appGuid) == null) {
      fetchPrivateKey(appGuid);
    }
    PrivateKeyResponse privateKeyResponse = privateKeys.get(appGuid);
    return loadPrivateKey(privateKeyResponse.getPrivateKey());
  }
}
