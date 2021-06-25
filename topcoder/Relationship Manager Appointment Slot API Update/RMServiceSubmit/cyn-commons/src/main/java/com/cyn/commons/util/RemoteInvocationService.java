package com.cyn.commons.util;

import static com.cyn.commons.exception.ErrorCode.DEC_ERR000001;
import static com.cyn.commons.exception.ErrorCode.ENC_ERR000001;
import static com.cyn.commons.security.SecurityHeaderConstants.ID_TOKEN_HEADER;
import static com.cyn.commons.security.SecurityHeaderConstants.X_APP_HEADER;
import static com.cyn.commons.security.SecurityHeaderConstants.X_AUTHORIZATION_HEADER;
import static com.cyn.commons.security.SecurityHeaderConstants.X_CYN_EVENT_HEADER;
import static com.cyn.commons.security.SecurityHeaderConstants.X_GUID_HEADER;
import static com.cyn.commons.security.SecurityHeaderConstants.X_RHYTHM_HEADER;

import com.cyn.commons.api.GenericResponse;
import com.cyn.commons.exception.BaseClientEncryptionException;
import com.cyn.commons.exception.BaseServiceException;
import com.cyn.commons.exception.ErrorCode;
import com.cyn.commons.filter.TokenPair;
import com.cyn.commons.security.PublicKeyResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@ConditionalOnExpression("${remote.client.enabled:true}")
@Getter
/** Service that is responsible for abstracting the communication between services */
public class RemoteInvocationService {

  // The response type reference for AEKM Service
  private static final ParameterizedTypeReference<GenericResponse<PublicKeyResponse>>
      PUB_KEY_RESP_TYPE = new ParameterizedTypeReference<>() {};

  // Algorithm for async encryption
  public static final String RSA = "RSA";

  // Get public key endpoint for getting public key of current service
  public static final String AEKM_GET_PUBLIC_KEY_URL =
      "http://%s/v1/security/service/aekm/publickey?appId=%s";

  // Character pool for generating random symmetric key
  public static final String ALPHA_NUMERIC_STRING_POOL =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

  // The AppId of host service for fetching public key from AEKM Service to be used for symmetric
  // key encryption
  private final String appId;

  // Utility for rsa algorithm
  private final RSAUtils rsaUtils;

  // Utility for aes algorithm
  private final AESUtils aesUtils;

  // Authentication utils for access token
  private final AuthenticationUtils authenticationUtils;

  // Rest template for remote calls
  private final RestTemplate restTemplate;

  // The current public key to be used for symmetric key encryption
  private PublicKeyResponse latestPublicKey;

  // Aekm server address or istio service name
  private final String aekmServer;
  // Random key for symmetric encryption
  private final String randomSymmetricKey;

  /**
   * The constructor for consuming other cyn services
   *
   * @param rsaUtils the utility class for RSA encryption
   * @param aekmServer the hostname(for local development)/service (for istio) name of AEKM server
   * @param appId the AppId for the caller service
   * @param aesUtils the utility class for AES Symmetric Encryption
   * @param authenticationUtils
   * @param restTemplate
   * @throws NoSuchAlgorithmException
   */
  public RemoteInvocationService(
      RSAUtils rsaUtils,
      @Value("${aekm.server.name}") String aekmServer,
      @Value("${aekm.app.id}") String appId,
      AESUtils aesUtils,
      AuthenticationUtils authenticationUtils,
      RestTemplate restTemplate)
      throws NoSuchAlgorithmException {
    this.rsaUtils = rsaUtils;
    this.aekmServer = aekmServer;
    this.aesUtils = aesUtils;
    this.authenticationUtils = authenticationUtils;
    this.restTemplate = restTemplate;
    this.randomSymmetricKey = getAlphaNumericString(16); // The random symmetric key
    this.appId = appId;
  }

  /**
   * The initializer for remove invocation service, fetches initial public key during boot for
   * reducing the request time for the first request
   */
  @PostConstruct
  public void init() {
    try {
      getPublicKey();
    } catch (Exception e) {
      log.warn(
          "Public key could not be fetched during boot, will be retried with the first request",
          e.getMessage());
    }
  }

  /**
   * The main logic for getting public key. If the key is expired or not exist, it's reloaded from
   * AEKM server
   *
   * @return the structure containing public key and details of key
   */
  private PublicKeyResponse getPublicKey() {
    if (latestPublicKey == null
        || LocalDateTime.parse(latestPublicKey.getAppKey().getValidTo())
            .isBefore(LocalDateTime.now())) {
      GenericResponse<PublicKeyResponse> response =
          restTemplate
              .exchange(
                  String.format(AEKM_GET_PUBLIC_KEY_URL, aekmServer, appId),
                  HttpMethod.GET,
                  null,
                  PUB_KEY_RESP_TYPE)
              .getBody();
      if (response != null) {
        latestPublicKey = response.getBody();
      } else {
        log.warn("Public key could not be refreshed, will be retried with the first request");
      }
    }
    return latestPublicKey;
  }

  /**
   * The remote service executor method that takes care of all the internal encryption and response
   * mapping
   *
   * @param <T> Generic Type of the response
   * @param remoteInvocationContext the context objects hold the necessary inputs
   * @return the expected response entity
   * @throws IOException
   * @throws GeneralSecurityException
   */
  public <T> ResponseEntity<T> executeRemoteService(
      RemoteInvocationContext<T> remoteInvocationContext) {
    PublicKeyResponse publicKey = getPublicKey();
    if (publicKey == null) {
      throw new BaseClientEncryptionException(ENC_ERR000001.toString(), Collections.emptyList());
    }
    byte[] requestBodyEncrypted;
    byte[] symmetricKeyEncrypted;
    try {
      byte[] jsonValue =
          JsonUtils.OBJECT_MAPPER.writeValueAsBytes(remoteInvocationContext.getRequestBody());
      requestBodyEncrypted =
          aesUtils.encryptPKCS5(jsonValue, randomSymmetricKey.getBytes(StandardCharsets.UTF_8));
      symmetricKeyEncrypted =
          rsaUtils.encrypt(randomSymmetricKey.getBytes(), loadPublicKey(publicKey.getPublicKey()));
    } catch (GeneralSecurityException | JsonProcessingException e) {
      throw new BaseClientEncryptionException(ENC_ERR000001.toString(), Collections.emptyList());
    }
    HttpHeaders headers = new HttpHeaders();
    headers.add(X_GUID_HEADER, publicKey.getAppKey().getGuid().toString());
    headers.add(X_APP_HEADER, publicKey.getAppKey().getAppId());
    headers.add(X_RHYTHM_HEADER, Base64Utils.encodeToString(symmetricKeyEncrypted));
    if (!StringUtils.isEmpty(remoteInvocationContext.getCynEvent())) {
      headers.add(X_CYN_EVENT_HEADER, remoteInvocationContext.getCynEvent());
    }
    if (remoteInvocationContext.isIncludeAccessToken()) {
      if (validSecurityContextForReuse()) {
        headers.add(
            X_AUTHORIZATION_HEADER,
            ((TokenPair) SecurityContextHolder.getContext().getAuthentication().getCredentials())
                .getAccessToken());
        headers.add(
            ID_TOKEN_HEADER,
            ((TokenPair) SecurityContextHolder.getContext().getAuthentication().getCredentials())
                .getIdToken());
      } else {
        TokenPair tokens = getTokens();
        headers.add(X_AUTHORIZATION_HEADER, tokens.getAccessToken());
        headers.add(ID_TOKEN_HEADER, tokens.getIdToken());
      }
    }
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(List.of(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN));
    if (remoteInvocationContext.getHeaders() != null) {
      remoteInvocationContext.getHeaders().forEach(headers::add);
    }
    HttpEntity<String> entity =
        new HttpEntity<>(Base64Utils.encodeToString(requestBodyEncrypted), headers);

    ResponseEntity<String> response =
        restTemplate.exchange(
            remoteInvocationContext.getUrl(),
            remoteInvocationContext.getHttpMethod(),
            entity,
            String.class);
    try {
      return new ResponseEntity<>(
          JsonUtils.OBJECT_MAPPER.readValue(
              aesUtils.decryptPKCS5(
                  Base64.getDecoder().decode(response.getBody()),
                  randomSymmetricKey.getBytes(StandardCharsets.UTF_8)),
              remoteInvocationContext.getReturnType()),
          response.getStatusCode());
    } catch (GeneralSecurityException | IOException e) {
      throw new BaseClientEncryptionException(DEC_ERR000001.toString(), Collections.emptyList());
    }
  }

  /**
   * Logic to validate if there is a valid Security Context with id token and access token.
   *
   * @return flag to indicate if security context valid.
   */
  private boolean validSecurityContextForReuse() {
    return SecurityContextHolder.getContext().getAuthentication() != null
        && !(SecurityContextHolder.getContext().getAuthentication()
            instanceof AnonymousAuthenticationToken)
        && SecurityContextHolder.getContext().getAuthentication().getCredentials() != null
        && SecurityContextHolder.getContext().getAuthentication().getCredentials()
            instanceof TokenPair
        && ((TokenPair) SecurityContextHolder.getContext().getAuthentication().getCredentials())
                .getAccessToken()
            != null
        && ((TokenPair) SecurityContextHolder.getContext().getAuthentication().getCredentials())
                .getIdToken()
            != null;
  }

  /**
   * The helper function for generating random symmetric key
   *
   * @param n the length of random string
   * @return the generated random key
   * @throws NoSuchAlgorithmException
   */
  private String getAlphaNumericString(int n) throws NoSuchAlgorithmException {

    // create StringBuffer size of AlphaNumericString
    StringBuilder sb = new StringBuilder(n);

    for (int i = 0; i < n; i++) {

      // generate a random number between
      // 0 to AlphaNumericString variable length
      int index =
          (int)
              (ALPHA_NUMERIC_STRING_POOL.length() * SecureRandom.getInstanceStrong().nextDouble());

      // add Character one by one in end of sb
      sb.append(ALPHA_NUMERIC_STRING_POOL.charAt(index));
    }

    return sb.toString();
  }

  /**
   * A helper function for converting base64 encoded public key to java PublicKey interface
   *
   * @param key64 the base64 encoded public key
   * @return a PublicKey implementation
   * @throws GeneralSecurityException
   */
  private PublicKey loadPublicKey(String key64) throws GeneralSecurityException {
    byte[] clear = Base64.getDecoder().decode(key64);
    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(clear);
    KeyFactory fact = KeyFactory.getInstance(RSA);
    PublicKey publicKey = fact.generatePublic(keySpec);
    Arrays.fill(clear, (byte) 0);
    return publicKey;
  }

  /**
   * This method will fetch the token from authentication service using a pre configured username
   * and password.
   *
   * @return access token
   */
  public TokenPair getTokens() {
    try {
      ResponseEntity<Object> responseEntity =
          executeRemoteService(
              RemoteInvocationContext.builder()
                  .returnType(Object.class)
                  .requestBody(authenticationUtils.getAccessTokenRequest())
                  .httpMethod(HttpMethod.POST)
                  .url(authenticationUtils.getAuthenticationServerSystemLoginUrl())
                  .includeAccessToken(Boolean.FALSE)
                  .build());
      return authenticationUtils.extractTokens(responseEntity);
    } catch (Exception e) {
      throw new BaseServiceException(
          ErrorCode.ATH_ERR000005.toString(), Collections.emptyList(), e);
    }
  }
}
