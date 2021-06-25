package com.cyn.commons.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cyn.commons.util.AESUtils;
import com.cyn.commons.util.RemoteInvocationService;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@ExtendWith(MockitoExtension.class)
class CustomRestExceptionHandlerTest {

  @Mock private RemoteInvocationService remoteInvocationService;

  @Mock private AESUtils aesUtils;
  /** Tests if the exception handler is able to handle various service exceptions */
  @Test
  void testHandleServiceException() {
    CustomRestExceptionHandler customRestExceptionHandler =
        new CustomRestExceptionHandler(aesUtils, remoteInvocationService);
    ResponseEntity actualEntity =
        customRestExceptionHandler.handleServiceException(
            new BaseServiceException("OTP_ERR000001", Collections.EMPTY_LIST));
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualEntity.getStatusCode());
  }

  /** Tests if the exception handler is able to handle status failure exception */
  @Test
  void testHandleStatusFailureException() {
    CustomRestExceptionHandler customRestExceptionHandler =
        new CustomRestExceptionHandler(aesUtils, remoteInvocationService);
    ResponseEntity actualEntity =
        customRestExceptionHandler.handleStatusFailureException(
            new BaseFailureStatusException("OTP_ERR000001", Collections.EMPTY_LIST));
    assertEquals(HttpStatus.OK, actualEntity.getStatusCode());
  }

  /** Tests if the exception handler is able to handle bad request exception */
  @Test
  void testBadRequestException() {
    CustomRestExceptionHandler customRestExceptionHandler =
        new CustomRestExceptionHandler(aesUtils, remoteInvocationService);
    ResponseEntity actualEntity =
        customRestExceptionHandler.handleBadRequestException(
            new BaseBadRequestException("OTP_ERR000001", Collections.EMPTY_LIST));
    assertEquals(HttpStatus.BAD_REQUEST, actualEntity.getStatusCode());
  }

  /** Tests if the exception handler is able to handle forbidden exception */
  @Test
  void testHandleUnAuthorizedException() {
    CustomRestExceptionHandler customRestExceptionHandler =
        new CustomRestExceptionHandler(aesUtils, remoteInvocationService);
    ResponseEntity actualEntity =
        customRestExceptionHandler.handleUnAuthorizedException(
            new BaseUnauthorizedException("OTP_ERR000001", Collections.EMPTY_LIST));
    assertEquals(HttpStatus.FORBIDDEN, actualEntity.getStatusCode());
  }

  /** Tests if the exception handler is able to handle unauthorized exception */
  @Test
  void testHandleUnAuthenticatedException() {
    CustomRestExceptionHandler customRestExceptionHandler =
        new CustomRestExceptionHandler(aesUtils, remoteInvocationService);
    ResponseEntity actualEntity =
        customRestExceptionHandler.handleUnAuthenticatedException(
            new BaseUnauthenticatedException("OTP_ERR000001", Collections.EMPTY_LIST));
    assertEquals(HttpStatus.UNAUTHORIZED, actualEntity.getStatusCode());
  }

  /** Tests if the exception handler is able to handle http client exceptions */
  @Test
  void testHandleHttpClientException() {
    CustomRestExceptionHandler customRestExceptionHandler =
        new CustomRestExceptionHandler(aesUtils, remoteInvocationService);
    ResponseEntity actualEntity =
        customRestExceptionHandler.handleHttpClientException(
            new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualEntity.getStatusCode());

    customRestExceptionHandler = new CustomRestExceptionHandler(aesUtils, remoteInvocationService);
    actualEntity =
        customRestExceptionHandler.handleHttpClientException(
            new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualEntity.getStatusCode());
  }

  /** Tests if the exception handler is able to handle not found exception */
  @Test
  void testHandleNotFoundException() {
    CustomRestExceptionHandler customRestExceptionHandler =
        new CustomRestExceptionHandler(aesUtils, remoteInvocationService);
    ResponseEntity actualEntity =
        customRestExceptionHandler.handleNotFoundException(
            new BaseNotFoundException("OTP_ERR000001", Collections.EMPTY_LIST));
    assertEquals(HttpStatus.NOT_FOUND, actualEntity.getStatusCode());
  }

  /** Tests if the exception handler is able to handle any unknown errors */
  @Test
  void testHandleUnknownError() {
    CustomRestExceptionHandler customRestExceptionHandler =
        new CustomRestExceptionHandler(aesUtils, remoteInvocationService);
    ResponseEntity actualEntity =
        customRestExceptionHandler.handleUnknownError(
            new BaseNotFoundException("OTP_ERR000001", Collections.EMPTY_LIST));
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualEntity.getStatusCode());
  }
}
