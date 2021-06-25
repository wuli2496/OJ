package com.cyn.commons.security;

import static com.cyn.commons.exception.ErrorCode.ENC_ERR000001;
import static com.cyn.commons.security.SecurityHeaderConstants.X_APP_HEADER;
import static com.cyn.commons.security.SecurityHeaderConstants.X_GUID_HEADER;
import static com.cyn.commons.security.SecurityHeaderConstants.X_RHYTHM_HEADER;

import com.cyn.commons.api.GenericResponse;
import com.cyn.commons.api.ResponseCode;
import com.cyn.commons.api.Status;
import com.cyn.commons.exception.BaseServiceException;
import com.cyn.commons.util.AESUtils;
import com.cyn.commons.util.JsonUtils;
import com.cyn.commons.util.KeyManagementUtils;
import com.cyn.commons.util.RSAUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.apache.commons.codec.binary.Base64InputStream;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/** The main BodyAdvice implementation for intercepting response and encrypting it */
@ControllerAdvice
@ConditionalOnExpression("${response.encryption.enabled:true}")
public class PayloadEncryption implements ResponseBodyAdvice<Object> {

  /** Rsa utils for decrypting symmetric key */
  private final RSAUtils rsaUtils;

  /** AES utils for encrypting body */
  private final AESUtils aesUtils;

  /** Key Management Utils for getting the keys from keystore */
  private final KeyManagementUtils keyManagementUtils;

  /** Actuator health check * */
  private static final String ACTUATOR_HEALTH = "/actuator/health";

  /**
   * Constructor to be called by Spring
   *
   * @param keyManagementUtils key management utils instance for fetching keys from aekm
   * @param aesUtils aes utils instance
   * @param rsaUtils rsa utils instance
   */
  public PayloadEncryption(
      KeyManagementUtils keyManagementUtils, AESUtils aesUtils, RSAUtils rsaUtils) {
    this.keyManagementUtils = keyManagementUtils;
    this.aesUtils = aesUtils;
    this.rsaUtils = rsaUtils;
  }

  /**
   * Checking if response should be encrypted. Return true for all cyn services if enabled
   *
   * @param methodParameter input parameters of method
   * @param aClass class type of response
   * @return always true
   */
  @Override
  public boolean supports(
      MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
    return true;
  }

  /**
   * The entrypoint for encrypting the response returned by controller
   *
   * @param response response object
   * @param methodParameter method parameters
   * @param mediaType content type of response
   * @param aClass return type if controller
   * @param serverHttpRequest actual http request
   * @param serverHttpResponse actual http reesponse
   * @return base64 encoded data
   */
  @Override
  public Object beforeBodyWrite(
      Object response,
      MethodParameter methodParameter,
      MediaType mediaType,
      Class<? extends HttpMessageConverter<?>> aClass,
      ServerHttpRequest serverHttpRequest,
      ServerHttpResponse serverHttpResponse) {

    if (serverHttpRequest.getURI().toString().contains(ACTUATOR_HEALTH)) {
      return response;
    }
    if (response instanceof GenericResponse) {
      processGenericResponseForStatusDetails((GenericResponse) response);
    }
    List<String> xAppHeader = serverHttpRequest.getHeaders().get(X_APP_HEADER);
    List<String> guidHeader = serverHttpRequest.getHeaders().get(X_GUID_HEADER);
    List<String> rhythm = serverHttpRequest.getHeaders().get(X_RHYTHM_HEADER);
    if (xAppHeader == null || guidHeader == null || rhythm == null) {
      serverHttpResponse.setStatusCode(HttpStatus.BAD_REQUEST);
      return null;
    }
    return encryptBodyUsingHeaders(
        response, xAppHeader.get(0), guidHeader.get(0), rhythm.get(0), serverHttpResponse);
  }

  /**
   * Encrypt body with the app information
   *
   * @param response data to encrypt
   * @param xApp application name
   * @param xGuid application guid
   * @param xRhythm encrypted symmetric key of requestor
   * @param serverHttpResponse the http response object
   * @return Base64 encoded body
   */
  public Object encryptBodyUsingHeaders(
      Object response,
      String xApp,
      String xGuid,
      String xRhythm,
      ServerHttpResponse serverHttpResponse) {

    AppGuid appGuid = new AppGuid(xApp, UUID.fromString(xGuid));

    byte[] symmetricKey;
    try {
      PrivateKey privateKeyResponse = keyManagementUtils.getPrivateKey(appGuid);
      symmetricKey = rsaUtils.decrypt(Base64.getDecoder().decode(xRhythm), privateKeyResponse);
    } catch (Exception e) {
      throw new BaseServiceException(ENC_ERR000001.toString(), Collections.emptyList(), e);
    }

    try {
      byte[] encoded;
      if (response instanceof InputStreamResource) {
        var input = ((InputStreamResource) response).getInputStream();
        var encrypted = aesUtils.encryptPKCS5(input, symmetricKey);
        var base64 = new Base64InputStream(encrypted, true);
        try (base64) {
          var t = new ByteArrayOutputStream();
          base64.transferTo(t);
          encoded = t.toByteArray();
        }
        serverHttpResponse.getHeaders().setContentLength(encoded.length);
        return new InputStreamResource(new ByteArrayInputStream(encoded));
      } else {
        encoded =
            Base64.getEncoder()
                .encode(
                    aesUtils.encryptPKCS5(
                        JsonUtils.OBJECT_MAPPER.writeValueAsBytes(response), symmetricKey));
        serverHttpResponse.getHeaders().setContentLength(encoded.length);
        return new String(encoded);
      }
    } catch (Exception e) {
      throw new BaseServiceException(ENC_ERR000001.toString(), Collections.emptyList(), e);
    }
  }

  /**
   * Add status fields for a generic response in success case
   *
   * @param response generic response content
   */
  private void processGenericResponseForStatusDetails(GenericResponse<?> response) {
    if (response.getStatus() == null) {
      response.setStatus(Status.builder().code(ResponseCode.SUCCESS).build());
    } else if (response.getStatus().getCode() == null) {
      response.getStatus().setCode(ResponseCode.SUCCESS);
    }
  }
}
