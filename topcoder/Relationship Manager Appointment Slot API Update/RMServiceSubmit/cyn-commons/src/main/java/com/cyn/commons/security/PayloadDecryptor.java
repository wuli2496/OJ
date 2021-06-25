package com.cyn.commons.security;

import static com.cyn.commons.exception.ErrorCode.DEC_ERR000001;
import static com.cyn.commons.security.SecurityHeaderConstants.X_APP_HEADER;
import static com.cyn.commons.security.SecurityHeaderConstants.X_GUID_HEADER;
import static com.cyn.commons.security.SecurityHeaderConstants.X_RHYTHM_HEADER;

import com.cyn.commons.exception.BaseServiceException;
import com.cyn.commons.util.AESUtils;
import com.cyn.commons.util.KeyManagementUtils;
import com.cyn.commons.util.RSAUtils;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

/** Payload decryptor for decrypting payload */
@Slf4j
@ControllerAdvice
public class PayloadDecryptor extends RequestBodyAdviceAdapter {

  /** Rsa utils for decrypting symmetric key */
  private final RSAUtils rsaUtils;

  /** AES utils for decrypting body */
  private final AESUtils aesUtils;

  /** Key Management Utils for getting the keys from aekm */
  private final KeyManagementUtils keyManagementUtils;

  /**
   * Constructor to be called by spring
   *
   * @param rsaUtils rsa utils instance
   * @param aesUtils aes utils instance
   * @param keyManagementUtils key management utils instance for fetching keys from aekm
   */
  public PayloadDecryptor(
      RSAUtils rsaUtils, AESUtils aesUtils, KeyManagementUtils keyManagementUtils) {
    this.rsaUtils = rsaUtils;
    this.aesUtils = aesUtils;
    this.keyManagementUtils = keyManagementUtils;
  }

  /**
   * Logic for determining the method should decrypt payload.
   *
   * @param methodParameter parameter of the method
   * @param type type information
   * @param aClass class type
   * @return if supports
   */
  @Override
  public boolean supports(
      final MethodParameter methodParameter,
      final Type type,
      final Class<? extends HttpMessageConverter<?>> aClass) {
    return methodParameter.getContainingClass().getAnnotation(PayloadDecryption.class) != null
        || methodParameter.getMethodAnnotation(PayloadDecryption.class) != null;
  }

  /**
   * Logic for decrypting payload.
   *
   * @param inputMessage http message input
   * @param parameter method parameters of controller
   * @param targetType input type of controller
   * @param converterType converter selected
   * @return decrypted payload
   * @throws IOException IO error can occur while reading from request stream
   */
  @Override
  public HttpInputMessage beforeBodyRead(
      final HttpInputMessage inputMessage,
      final MethodParameter parameter,
      final Type targetType,
      final Class<? extends HttpMessageConverter<?>> converterType)
      throws IOException {
    InputStream body = inputMessage.getBody();
    InputStream base64Decoded = Base64.getDecoder().wrap(body);
    List<String> xAppHeader = inputMessage.getHeaders().get(X_APP_HEADER);
    List<String> guidHeader = inputMessage.getHeaders().get(X_GUID_HEADER);
    List<String> rhythm = inputMessage.getHeaders().get(X_RHYTHM_HEADER);
    if (xAppHeader == null || guidHeader == null || rhythm == null) {
      throw new BaseServiceException(DEC_ERR000001.toString(), Collections.emptyList());
    }
    String xApp = xAppHeader.get(0);
    String xGuid = guidHeader.get(0);
    String xRhythm = rhythm.get(0);

    AppGuid appGuid = new AppGuid(xApp, UUID.fromString(xGuid));
    try {
      PrivateKey privateKey = keyManagementUtils.getPrivateKey(appGuid);
      byte[] symmetricKey = rsaUtils.decrypt(Base64.getDecoder().decode(xRhythm), privateKey);
      return new MappingJacksonInputMessage(
          aesUtils.decryptPKCS5(base64Decoded, symmetricKey), inputMessage.getHeaders());
    } catch (Exception e) {
      throw new BaseServiceException(DEC_ERR000001.toString(), Collections.emptyList(), e);
    }
  }
}
