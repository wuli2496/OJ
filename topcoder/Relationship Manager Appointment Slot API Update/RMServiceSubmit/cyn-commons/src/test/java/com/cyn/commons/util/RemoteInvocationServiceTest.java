package com.cyn.commons.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;

import com.cyn.commons.api.GenericResponse;
import com.cyn.commons.filter.TokenPair;
import com.cyn.commons.security.AppKey;
import com.cyn.commons.security.PublicKeyResponse;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class RemoteInvocationServiceTest {

  @InjectMocks private RemoteInvocationService remoteInvocationService;

  @Mock private RestTemplate restTemplate;

  @Mock private AESUtils aesUtils;

  @Mock private RSAUtils rsaUtils;

  @Mock private AuthenticationUtils authenticationUtils;

  private final String publicKey =
      "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgFhTDtuYJ5G5LEHMesnf"
          + "wX9cXZ1b/ozkkpbgtC3ziETiFkOFncbPCskpdbPmXxXv3vrwJ2RQIL2LZLZPe1xT"
          + "AyQY1DdD8hGqIemMwV2NqfFoEomVL5+QOAKCRiHkGgte6a2+OoTk9JzRP/NVaPkB"
          + "sdX1/nIPERYen3uDvUSYq83Ite2oDyaZZxj+/r46SadS/g5jWmeqgVoInJw813y7"
          + "Ee2HgYVbnktlLNhqIGj+1OKmwop+GP7Kk5CAkt9fo4VjRRllDaX1yFCZEbDL254n"
          + "S+LVOhl4mLBM8764+YVxjyYRC1Nq2rNZfQ602652i+l8u8nGqdiKOKDpjNDvhONP"
          + "yQIDAQAB";

  @Test
  void executeRemoteService() throws GeneralSecurityException {
    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken("principal", "Bearer token"));

    RemoteInvocationContext remoteInvocationContext = new RemoteInvocationContext();
    remoteInvocationContext.setHttpMethod(HttpMethod.POST);
    remoteInvocationContext.setUrl("");
    remoteInvocationContext.setReturnType(Map.class);
    remoteInvocationContext.setHeaders(
        new HashMap<>() {
          {
            put("Content-Type", "application/json");
          }
        });
    remoteInvocationContext.setCynEvent("cynEvent");
    remoteInvocationContext.setIncludeAccessToken(true);

    doReturn("url").when(authenticationUtils).getAuthenticationServerSystemLoginUrl();

    doReturn("requestEncrypted".getBytes()).when(aesUtils).encryptPKCS5(any(byte[].class), any());
    doReturn("{ \"key\": \"value\" }".getBytes())
        .when(aesUtils)
        .decryptPKCS5(any(byte[].class), any());
    doReturn("symmetricEncrypted".getBytes()).when(rsaUtils).encrypt(any(), any());

    GenericResponse<PublicKeyResponse> response = new GenericResponse<>();
    response.setBody(new PublicKeyResponse());
    response.getBody().setAppKey(new AppKey());
    response.getBody().getAppKey().setGuid(UUID.randomUUID());
    response
        .getBody()
        .getAppKey()
        .setValidTo(
            LocalDateTime.now().minus(2, ChronoUnit.DAYS).format(DateUtils.UTC_DATETIME_FORMATTER));
    response.getBody().setPublicKey(publicKey);

    ResponseEntity<GenericResponse> responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

    doReturn(responseEntity)
        .when(restTemplate)
        .exchange(
            anyString(),
            eq(HttpMethod.GET),
            any(),
            eq(new ParameterizedTypeReference<GenericResponse<PublicKeyResponse>>() {}));

    ResponseEntity<String> remoteResponse =
        new ResponseEntity<>(
            Base64.getEncoder().encodeToString("helloWorld".getBytes()), HttpStatus.OK);

    doReturn(remoteResponse)
        .when(restTemplate)
        .exchange(anyString(), eq(HttpMethod.POST), any(), eq(String.class));

    remoteInvocationService.init();

    doReturn(TokenPair.builder().accessToken("accessToken").idToken("idToken").build())
        .when(authenticationUtils)
        .extractTokens(any());

    ResponseEntity<Map> actualResponse =
        remoteInvocationService.executeRemoteService(remoteInvocationContext);
    assertEquals(
        actualResponse.getBody(),
        new HashMap() {
          {
            put("key", "value");
          }
        });

    assertNull(remoteInvocationService.getAppId());
    assertNotNull(remoteInvocationService.getRsaUtils());
    assertNotNull(remoteInvocationService.getAesUtils());
    assertNotNull(remoteInvocationService.getAuthenticationUtils());
    assertNotNull(remoteInvocationService.getRestTemplate());
    assertNotNull(remoteInvocationService.getLatestPublicKey());
    assertNull(remoteInvocationService.getAekmServer());
    assertNotNull(remoteInvocationService.getRandomSymmetricKey());

    assertEquals("accessToken", remoteInvocationService.getTokens().getAccessToken());
    assertEquals("idToken", remoteInvocationService.getTokens().getIdToken());
  }
}
