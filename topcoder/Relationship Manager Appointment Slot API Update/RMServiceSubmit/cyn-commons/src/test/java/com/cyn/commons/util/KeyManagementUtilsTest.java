package com.cyn.commons.util;

import static junit.framework.TestCase.assertEquals;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;

import com.cyn.commons.api.GenericResponse;
import com.cyn.commons.security.AppGuid;
import com.cyn.commons.security.AppKey;
import com.cyn.commons.security.InternalSecretUtil;
import com.cyn.commons.security.PrivateKeyResponse;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
@RunWith(PowerMockRunner.class)
@PrepareForTest(InternalSecretUtil.class)
public class KeyManagementUtilsTest {

  private static final String PRIVATE_KEY =
      "-----BEGIN PRIVATE KEY-----\n"
          + "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAM7t8Ub1DP+B91NJ\n"
          + "nC45zqIvd1QXkQ5Ac1EJl8mUglWFzUyFbhjSuF4mEjrcecwERfRummASbLoyeMXl\n"
          + "eiPg7jvSaz2szpuV+afoUo9c1T+ORNUzq31NvM7IW6+4KhtttwbMq4wbbPpBfVXA\n"
          + "IAhvnLnCp/VyY/npkkjAid4c7RoVAgMBAAECgYBcCuy6kj+g20+G5YQp756g95oN\n"
          + "dpoYC8T/c9PnXz6GCgkik2tAcWJ+xlJviihG/lObgSL7vtZMEC02YXdtxBxTBNmd\n"
          + "upkruOkL0ElIu4S8CUwD6It8oNnHFGcIhwXUbdpSCr1cx62A0jDcMVgneQ8vv6vB\n"
          + "/YKlj2dD2SBq3aaCYQJBAOvc5NDyfrdMYYTY+jJBaj82JLtQ/6K1vFIwdxM0siRF\n"
          + "UYqSRA7G8A4ga+GobTewgeN6URFwWKvWY8EGb3HTwFkCQQDgmKtjjJlX3BotgnGD\n"
          + "gdxVgvfYG39BL2GnotSwUbjjce/yZBtrbcClfqrrOWWw7lPcX1d0v8o3hJfLF5dT\n"
          + "6NAdAkA8qAQYUCSSUwxJM9u0DOqb8vqjSYNUftQ9dsVIpSai+UitEEx8WGDn4SKd\n"
          + "V8kupy/gJlau22uSVYI148fJSCGRAkBz+GEHFiJX657YwPI8JWHQBcBUJl6fGggi\n"
          + "t0F7ibceOkbbsjU2U4WV7sHyk8Cei3Fh6RkPf7i60gxPIe9RtHVBAkAnPQD+BmND\n"
          + "By8q5f0Kwtxgo2+YkxGDP5bxDV6P1vd2C7U5/XxaN53Kc0G8zu9UlcwhZcQ5BljH\n"
          + "N24cUWZOo+60\n"
          + "-----END PRIVATE KEY-----";

  @Mock private RestTemplate restTemplate;

  @Test
  public void init() throws InterruptedException {
    KeyManagementUtils keyManagementUtils = new KeyManagementUtils("", 1);
    Map<AppGuid, PrivateKeyResponse> privateKeys =
        (Map) ReflectionTestUtils.getField(keyManagementUtils, "privateKeys");
    AppGuid appGuid = new AppGuid("appId", UUID.randomUUID());
    PrivateKeyResponse privateKeyResponse = new PrivateKeyResponse();
    privateKeyResponse.setAppKey(new AppKey());
    privateKeyResponse.getAppKey().setValidTo(LocalDateTime.now().toString());
    privateKeys.put(appGuid, privateKeyResponse);

    keyManagementUtils.init();

    await().atMost(62, TimeUnit.SECONDS).until(isPrivateKeyMapEmpty(keyManagementUtils));
  }

  private Callable<Boolean> isPrivateKeyMapEmpty(KeyManagementUtils keyManagementUtils) {
    return new Callable<Boolean>() {
      public Boolean call() throws Exception {
        Map privateKeys = (Map) ReflectionTestUtils.getField(keyManagementUtils, "privateKeys");
        return privateKeys.size() == 0;
      }
    };
  }

  @Test
  public void fetchAndLoadPrivateKey() throws GeneralSecurityException {
    PowerMockito.mockStatic(InternalSecretUtil.class);
    PowerMockito.when(InternalSecretUtil.encryptAppGuid(any())).thenReturn("encrypted");

    GenericResponse<PrivateKeyResponse> response = new GenericResponse<>();
    response.setBody(new PrivateKeyResponse());
    response.getBody().setAppKey(new AppKey());
    String pkcs8Pem = PRIVATE_KEY.replace("-----BEGIN PRIVATE KEY-----", "");
    pkcs8Pem = pkcs8Pem.replace("-----END PRIVATE KEY-----", "");
    pkcs8Pem = pkcs8Pem.replaceAll("\\s+", "");
    response.getBody().setPrivateKey(pkcs8Pem);

    ResponseEntity<GenericResponse> responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

    doReturn(responseEntity)
        .when(restTemplate)
        .exchange(
            anyString(),
            eq(HttpMethod.GET),
            any(),
            eq(new ParameterizedTypeReference<GenericResponse<PrivateKeyResponse>>() {}));
    AppGuid appGuid = new AppGuid("appId", UUID.randomUUID());
    KeyManagementUtils keyManagementUtils = new KeyManagementUtils("", 1);

    ReflectionTestUtils.setField(keyManagementUtils, "restTemplate", restTemplate);
    keyManagementUtils.fetchPrivateKey(appGuid);

    Map<AppGuid, PrivateKeyResponse> privateKeys =
        (Map) ReflectionTestUtils.getField(keyManagementUtils, "privateKeys");
    assertEquals(response.getBody(), privateKeys.get(appGuid));

    // in case private key is already cached, fetch from cache directly
    PrivateKey privateKey = keyManagementUtils.getPrivateKey(appGuid);
    assertNotNull(privateKey);

    // in case private key is not present in cache, fetch from API call
    appGuid = new AppGuid("appId", UUID.randomUUID());
    privateKey = keyManagementUtils.getPrivateKey(appGuid);
    assertNotNull(privateKey);
  }
}
