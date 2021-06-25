package com.cyn.commons.exception;

import static com.cyn.commons.exception.ExceptionConstants.BUNDLE_NAME;

import com.cyn.commons.api.GenericResponse;
import com.cyn.commons.api.ResponseCode;
import com.cyn.commons.api.Status;
import com.cyn.commons.util.AESUtils;
import com.cyn.commons.util.JsonUtils;
import com.cyn.commons.util.RemoteInvocationService;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.ResourceBundle;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The custom logic for handling all exceptions thrown by services or controllers in a spring boot
 * application. This class can be directly used by other services without extra configuration.
 * Microservices should create a resource bundle that maps error keys to messages.
 */
@ControllerAdvice
@RequiredArgsConstructor
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

  /** Error message for unknown exceptions * */
  private static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";

  /** Object key for error details */
  private static final String ERROR_DETAILS_UNAUTHENTICATED = "errorDetails";

  /** Default initial index for authentication error details */
  private static final int INITIAL_INDEX = 0;

  // Utility for aes algorithm
  private final AESUtils aesUtils;

  // Remote invocation service for symmetric key
  private final RemoteInvocationService remoteInvocationService;

  /**
   * Handle base service exception and return 500. Read messages from message bundle
   *
   * @param ex exception thrown
   * @return response entity to be send to caller
   */
  @ExceptionHandler({BaseServiceException.class})
  public ResponseEntity<Object> handleServiceException(BaseServiceException ex) {
    ResourceBundle rb = ResourceBundle.getBundle(BUNDLE_NAME);
    GenericResponse<Object> errorResponse = new GenericResponse<>();
    String exceptionMessage =
        rb.containsKey(ex.getErrorCode()) ? rb.getString(ex.getErrorCode()) : ex.getErrorMessage();
    errorResponse.setStatus(
        Status.builder()
            .code(ResponseCode.FAILURE)
            .errorCode(ex.getErrorCode())
            .message(exceptionMessage)
            .internalMessage(exceptionMessage)
            .build());
    return new ResponseEntity<>(
        errorResponse,
        new HttpHeaders(),
        ex.getHttpStatus() != null ? ex.getHttpStatus() : HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Handle failure exception and return 200. Read messages from message bundle.
   *
   * @param ex exception thrown
   * @return response entity to be send to caller
   */
  @ExceptionHandler({BaseFailureStatusException.class})
  public ResponseEntity<Object> handleStatusFailureException(BaseServiceException ex) {
    ResourceBundle rb = ResourceBundle.getBundle(BUNDLE_NAME);
    String exceptionMessage =
        rb.containsKey(ex.getErrorMessage())
            ? rb.getString(ex.getErrorMessage())
            : ex.getErrorMessage();
    GenericResponse<Object> errorResponse = new GenericResponse<>();
    errorResponse.setStatus(
        Status.builder()
            .code(ResponseCode.SUCCESS)
            .errorCode(ex.getErrorMessage())
            .message(exceptionMessage)
            .internalMessage(exceptionMessage)
            .build());
    return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.OK);
  }

  /**
   * Handle bad request exception and return 400. Read messages from message bundle.
   *
   * @param ex exception thrown
   * @return response entity to be send to caller
   */
  @ExceptionHandler({BaseBadRequestException.class})
  public ResponseEntity<Object> handleBadRequestException(BaseBadRequestException ex) {
    ResourceBundle rb = ResourceBundle.getBundle(BUNDLE_NAME);
    String exceptionMessage =
        rb.containsKey(ex.getErrorMessage())
            ? rb.getString(ex.getErrorMessage())
            : ex.getErrorMessage();
    GenericResponse<Object> errorResponse = new GenericResponse<>();
    errorResponse.setStatus(
        Status.builder()
            .code(ResponseCode.FAILURE)
            .errorCode(ex.getErrorMessage())
            .message(exceptionMessage)
            .internalMessage(exceptionMessage)
            .build());
    return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle unauthorized exception and return 403. Read messages from message bundle.
   *
   * @param ex exception thrown
   * @return response entity to be send to caller
   */
  @ExceptionHandler({BaseUnauthorizedException.class})
  public ResponseEntity<Object> handleUnAuthorizedException(BaseUnauthorizedException ex) {
    ResourceBundle rb = ResourceBundle.getBundle(BUNDLE_NAME);
    String exceptionMessage =
        rb.containsKey(ex.getErrorMessage())
            ? rb.getString(ex.getErrorMessage())
            : ex.getErrorMessage();
    GenericResponse<Object> errorResponse = new GenericResponse<>();
    errorResponse.setStatus(
        Status.builder()
            .code(ResponseCode.FAILURE)
            .errorCode(ex.getErrorMessage())
            .message(exceptionMessage)
            .internalMessage(exceptionMessage)
            .build());
    return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.FORBIDDEN);
  }

  /**
   * Handle unauthenticated exception and return 401. Read messages from message bundle.
   *
   * @param ex exception thrown
   * @return response entity to be send to caller
   */
  @ExceptionHandler({BaseUnauthenticatedException.class})
  public ResponseEntity<Object> handleUnAuthenticatedException(BaseUnauthenticatedException ex) {
    ResourceBundle rb = ResourceBundle.getBundle(BUNDLE_NAME);
    String exceptionMessage =
        rb.containsKey(ex.getErrorCode()) ? rb.getString(ex.getErrorCode()) : ex.getErrorMessage();
    GenericResponse<Object> errorResponse = new GenericResponse<>();
    errorResponse.setStatus(
        Status.builder()
            .code(ResponseCode.FAILURE)
            .errorCode(ex.getErrorCode())
            .message(exceptionMessage)
            .internalMessage(exceptionMessage)
            .build());
    if (ex.getSubErrors() != null && !ex.getSubErrors().isEmpty()) {
      errorResponse.setBody(
          Map.of(
              ERROR_DETAILS_UNAUTHENTICATED,
              ex.getSubErrors().size() > 1
                  ? ex.getSubErrors()
                  : ex.getSubErrors().get(INITIAL_INDEX).getErrorCode()));
    }
    return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
  }

  /**
   * Handle http client error and pass error as is to upstream.
   *
   * @param ex exception thrown
   * @return response entity to be send to caller
   */
  @ExceptionHandler({HttpClientErrorException.class})
  public ResponseEntity<Object> handleHttpClientException(HttpClientErrorException ex) {
    try {
      return new ResponseEntity<>(
          JsonUtils.OBJECT_MAPPER.readValue(
              aesUtils.decryptPKCS5(
                  Base64.getDecoder().decode(ex.getResponseBodyAsString()),
                  remoteInvocationService.getRandomSymmetricKey().getBytes(StandardCharsets.UTF_8)),
              Object.class),
          HttpStatus.valueOf(ex.getRawStatusCode()));
    } catch (Exception e) {
      GenericResponse<Object> errorResponse = new GenericResponse<>();
      errorResponse.setStatus(
          Status.builder()
              .code(ResponseCode.FAILURE)
              .errorCode(ex.getMessage())
              .message(UNKNOWN_ERROR)
              .internalMessage(ex.getMessage())
              .build());
      return new ResponseEntity<>(
          errorResponse, new HttpHeaders(), HttpStatus.valueOf(ex.getRawStatusCode()));
    }
  }

  /**
   * Handle http server error and pass error as is to upstream.
   *
   * @param ex exception thrown
   * @return response entity to be send to caller
   */
  @ExceptionHandler({HttpServerErrorException.class})
  public ResponseEntity<Object> handleHttpClientException(HttpServerErrorException ex) {
    try {
      return new ResponseEntity<>(
          JsonUtils.OBJECT_MAPPER.readValue(
              aesUtils.decryptPKCS5(
                  Base64.getDecoder().decode(ex.getResponseBodyAsString()),
                  remoteInvocationService.getRandomSymmetricKey().getBytes(StandardCharsets.UTF_8)),
              Object.class),
          HttpStatus.valueOf(ex.getRawStatusCode()));
    } catch (Exception e) {
      GenericResponse<Object> errorResponse = new GenericResponse<>();
      errorResponse.setStatus(
          Status.builder()
              .code(ResponseCode.FAILURE)
              .errorCode(ex.getMessage())
              .message(UNKNOWN_ERROR)
              .internalMessage(ex.getMessage())
              .build());
      return new ResponseEntity<>(
          errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Handle not found error and return 404.
   *
   * @param ex exception thrown
   * @return response entity to be send to caller
   */
  @ExceptionHandler({BaseNotFoundException.class})
  public ResponseEntity<Object> handleNotFoundException(BaseNotFoundException ex) {
    ResourceBundle rb = ResourceBundle.getBundle(BUNDLE_NAME);
    String exceptionMessage =
        rb.containsKey(ex.getErrorMessage())
            ? rb.getString(ex.getErrorMessage())
            : ex.getErrorMessage();
    GenericResponse<Object> errorResponse = new GenericResponse<>();
    errorResponse.setStatus(
        Status.builder()
            .code(ResponseCode.FAILURE)
            .errorCode(ex.getErrorMessage())
            .message(exceptionMessage)
            .internalMessage(exceptionMessage)
            .build());
    return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
  }

  /**
   * Handle general exceptions that are not caught by services.
   *
   * @param ex exception thrown
   * @return response entity to be send to caller
   */
  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> handleUnknownError(Exception ex) {
    GenericResponse<Object> errorResponse = new GenericResponse<>();
    errorResponse.setStatus(
        Status.builder()
            .code(ResponseCode.FAILURE)
            .errorCode(ex.getMessage())
            .message(UNKNOWN_ERROR)
            .internalMessage(ex.getMessage())
            .build());
    return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
