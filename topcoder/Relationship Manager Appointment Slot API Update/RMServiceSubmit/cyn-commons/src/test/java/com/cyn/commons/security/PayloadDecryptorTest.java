package com.cyn.commons.security;

import static com.cyn.commons.security.SecurityHeaderConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.cyn.commons.exception.BaseServiceException;
import com.cyn.commons.util.AESUtils;
import com.cyn.commons.util.KeyManagementUtils;
import com.cyn.commons.util.RSAUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
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
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.mock.http.MockHttpInputMessage;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class PayloadDecryptorTest {

  private PayloadDecryptor payloadDecryptor;

  @Mock private KeyManagementUtils keyManagementUtils;

  @Mock private RSAUtils rsaUtils;

  @Mock private PrivateKey privateKey;

  @Mock private AESUtils aesUtils;

  @BeforeEach()
  public void setup() {
    MockitoAnnotations.initMocks(PayloadDecryptor.class);
    this.payloadDecryptor = new PayloadDecryptor(rsaUtils, aesUtils, keyManagementUtils);
  }

  /**
   * Tests if a particular method or class has the annotation present. TestController2 is having the
   * annotation at method level. TestController2 is having the annotation at class level.
   */
  @Test
  void testSupports() {
    TestController1 testController1 = new TestController1();
    TestController2 testController2 = new TestController2();

    Method method = testController1.getClass().getMethods()[0];

    MethodParameter methodParam = MethodParameter.forExecutable(method, 0);

    boolean actualResult = payloadDecryptor.supports(methodParam, null, null);
    assertTrue(actualResult);

    method = testController1.getClass().getMethods()[1];
    methodParam = MethodParameter.forExecutable(method, 0);

    actualResult = payloadDecryptor.supports(methodParam, null, null);
    assertFalse(actualResult);

    method = testController2.getClass().getMethods()[0];
    methodParam = MethodParameter.forExecutable(method, 0);

    actualResult = payloadDecryptor.supports(methodParam, null, null);
    assertTrue(actualResult);
  }

  /**
   * Tests the happy code path by asserting on the payload returned by PayloadDecryptor and verifies
   * if the payload is correctly decrypted by the public key.
   */
  @Test
  void testBeforeBodyRead() throws Exception {
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
    when(aesUtils.decryptPKCS5(any(InputStream.class), any()))
        .thenReturn(new ByteArrayInputStream(input.getBytes()));

    HttpInputMessage actualMessage =
        this.payloadDecryptor.beforeBodyRead(httpInputMessage, null, null, null);
    assertEquals(input, new String(actualMessage.getBody().readAllBytes()));
  }

  /**
   * Tests if keyManagementUtils.getTrustedPublicKey returns an invalid key, then an exception of
   * type BaseServiceException is thrown
   */
  @Test
  void testBeforeBodyReadWithKeyNull() throws IOException {
    String input = "hello world";
    InputStream body =
        new ByteArrayInputStream(Base64.getEncoder().encodeToString(input.getBytes()).getBytes());
    HttpInputMessage httpInputMessage = new MockHttpInputMessage(body);

    BaseServiceException exception =
        assertThrows(
            BaseServiceException.class,
            () -> this.payloadDecryptor.beforeBodyRead(httpInputMessage, null, null, null));
    assertEquals("DEC_ERR000001", exception.getErrorMessage());
    assertEquals(Collections.EMPTY_LIST, exception.getSubErrors());
  }

  /**
   * Tests whether general security exception is handled correctly By mocking rsaUtils.decrypt
   * method, we ensure that GeneralSecurityException is thrown when beforeBodyRead is called.
   *
   * @throws GeneralSecurityException security exception
   */
  @Test
  void testBeforeBodyReadWithException() throws GeneralSecurityException, IOException {
    String input = "hello world";
    InputStream body =
        new ByteArrayInputStream(Base64.getEncoder().encodeToString(input.getBytes()).getBytes());
    HttpInputMessage httpInputMessage = new MockHttpInputMessage(body);

    BaseServiceException exception =
        assertThrows(
            BaseServiceException.class,
            () -> this.payloadDecryptor.beforeBodyRead(httpInputMessage, null, null, null));
    assertEquals("DEC_ERR000001", exception.getErrorMessage());
    assertEquals(Collections.EMPTY_LIST, exception.getSubErrors());
  }
}
