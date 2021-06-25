package com.cyn.commons.security;

import static com.cyn.commons.security.SecurityHeaderConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.cyn.commons.api.GenericResponse;
import com.cyn.commons.api.Status;
import com.cyn.commons.authentication.LoginResponse;
import com.cyn.commons.util.AESUtils;
import com.cyn.commons.util.KeyManagementUtils;
import com.cyn.commons.util.RSAUtils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.util.LinkedMultiValueMap;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class PayloadEncryptionTest {

  private PayloadEncryption payloadEncryption;

  @Mock private KeyManagementUtils keyManagementUtils;

  @Mock private RSAUtils rsaUtils;

  @Mock private PrivateKey privateKey;

  @Mock private AESUtils aesUtils;

  @Mock private ServerHttpRequest serverHttpRequest;

  @Mock private ServerHttpResponse serverHttpResponse;

  @BeforeEach()
  public void setup() {
    MockitoAnnotations.initMocks(PayloadDecryptor.class);
    this.payloadEncryption = new PayloadEncryption(keyManagementUtils, aesUtils, rsaUtils);
  }

  @Test
  void supports() {
    assertTrue(payloadEncryption.supports(null, null));
  }

  @Test
  void beforeBodyWriteForActuator() {
    when(serverHttpRequest.getURI())
        .thenReturn(URI.create("https://www.google.com/actuator/health"));
    String response = "response";

    Object actualMessage =
        this.payloadEncryption.beforeBodyWrite(
            response, null, null, null, serverHttpRequest, serverHttpResponse);
    assertEquals(response, actualMessage);
  }

  @Test
  void beforeBodyWriteWithNoHeaders() throws GeneralSecurityException {
    when(serverHttpRequest.getURI()).thenReturn(URI.create("https://www.google.com"));
    when(serverHttpRequest.getHeaders())
        .thenReturn(
            new HttpHeaders(
                new LinkedMultiValueMap<>() {
                  {
                  }
                }));
    String response = "response";

    Object actualMessage =
        this.payloadEncryption.beforeBodyWrite(
            response, null, null, null, serverHttpRequest, serverHttpResponse);
    assertNull(actualMessage);
  }

  @Test
  void beforeBodyWrite() throws GeneralSecurityException {
    String input = "hello world";

    when(keyManagementUtils.getPrivateKey(any())).thenReturn(privateKey);
    when(rsaUtils.decrypt(any(), any())).thenReturn(input.getBytes());
    when(aesUtils.encryptPKCS5(any(byte[].class), any())).thenReturn(input.getBytes());

    when(serverHttpRequest.getURI()).thenReturn(URI.create("https://www.google.com"));
    when(serverHttpRequest.getHeaders())
        .thenReturn(
            new HttpHeaders(
                new LinkedMultiValueMap<>() {
                  {
                    put(X_APP_HEADER, Collections.singletonList("app_header"));
                    put(X_GUID_HEADER, Collections.singletonList(UUID.randomUUID().toString()));
                    put(
                        X_RHYTHM_HEADER,
                        Collections.singletonList(
                            Base64.getEncoder().encodeToString("rhytm_header".getBytes())));
                  }
                }));
    when(serverHttpResponse.getHeaders()).thenReturn(new HttpHeaders());
    String response = "response";

    Object actualMessage =
        this.payloadEncryption.beforeBodyWrite(
            response, null, null, null, serverHttpRequest, serverHttpResponse);
    assertEquals("aGVsbG8gd29ybGQ=", actualMessage);
  }

  @Test
  void beforeBodyWriteForGenericResponse() throws GeneralSecurityException {
    String input = "hello world";
    InputStream body =
        new ByteArrayInputStream(Base64.getEncoder().encodeToString(input.getBytes()).getBytes());
    HttpInputMessage httpInputMessage = new MockHttpInputMessage(body);
    httpInputMessage.getHeaders().add(X_APP_HEADER, "app_header");
    httpInputMessage.getHeaders().add(X_GUID_HEADER, UUID.randomUUID().toString());
    httpInputMessage
        .getHeaders()
        .add(X_RHYTHM_HEADER, Base64.getEncoder().encodeToString("rhytm_header".getBytes()));

    when(keyManagementUtils.getPrivateKey(any())).thenReturn(privateKey);
    when(rsaUtils.decrypt(any(), any())).thenReturn(input.getBytes());
    when(aesUtils.encryptPKCS5(any(byte[].class), any())).thenReturn(input.getBytes());

    when(serverHttpRequest.getURI()).thenReturn(URI.create("https://www.google.com"));
    when(serverHttpRequest.getHeaders())
        .thenReturn(
            new HttpHeaders(
                new LinkedMultiValueMap<>() {
                  {
                    put(X_APP_HEADER, Collections.singletonList("app_header"));
                    put(X_GUID_HEADER, Collections.singletonList(UUID.randomUUID().toString()));
                    put(
                        X_RHYTHM_HEADER,
                        Collections.singletonList(
                            Base64.getEncoder().encodeToString("rhytm_header".getBytes())));
                  }
                }));
    when(serverHttpResponse.getHeaders()).thenReturn(new HttpHeaders());
    GenericResponse<LoginResponse> response = new GenericResponse<>();

    Object actualMessage =
        this.payloadEncryption.beforeBodyWrite(
            response, null, null, null, serverHttpRequest, serverHttpResponse);
    assertEquals("aGVsbG8gd29ybGQ=", actualMessage);

    response.setStatus(Status.builder().build());
    actualMessage =
        this.payloadEncryption.beforeBodyWrite(
            response, null, null, null, serverHttpRequest, serverHttpResponse);
    assertEquals("aGVsbG8gd29ybGQ=", actualMessage);
  }
}
