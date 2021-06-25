package com.cyn.commons.exception;

import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 * The base exception model to be used by all microservices. Other services can extend this class
 * and create use-case specific exceptions.
 */
public class BaseServiceException extends RuntimeException {

  /** Serial version of class */
  private static final long serialVersionUID = -5558520644090378015L;

  /** Error code of the exception */
  private final String errorCode;

  /** Error message of the exception */
  private final String errorMessage;

  /** HTTP Status of the exception */
  private final HttpStatus httpStatus;

  /** Sub errors of the exception */
  private final List<SubError> subErrors;

  /**
   * Constructor with only error code. Error message will be the same with code as it's not set
   *
   * @param errorCode unique error code for failure
   */
  public BaseServiceException(String errorCode) {
    super(errorCode);
    this.errorMessage = errorCode;
    this.errorCode = errorCode;
    this.subErrors = Collections.emptyList();
    httpStatus = null;
  }

  /**
   * Constructor with only error code and http status. Error message will be the same with code as
   * it's not set
   *
   * @param errorCode unique error code for failure
   * @param httpStatus http status of exception
   */
  public BaseServiceException(String errorCode, HttpStatus httpStatus) {
    super(errorCode);
    this.errorMessage = errorCode;
    this.errorCode = errorCode;
    this.subErrors = Collections.emptyList();
    this.httpStatus = httpStatus;
  }

  /**
   * Constructor with only error code and sub errors. Error message will be the same with code as
   * it's not set
   *
   * @param errorCode unique error code for failure
   * @param apiSubErrorList sub errors if exists
   */
  public BaseServiceException(String errorCode, List<SubError> apiSubErrorList) {
    super(errorCode);
    this.errorMessage = errorCode;
    this.errorCode = errorCode;
    this.subErrors = apiSubErrorList;
    httpStatus = null;
  }

  /**
   * Constructor with error code and a root cause
   *
   * @param errorCode unique error code for failure
   * @param apiSubErrorList sub errors if exists
   * @param cause a cause exception
   */
  public BaseServiceException(String errorCode, List<SubError> apiSubErrorList, Throwable cause) {
    super(errorCode, cause);
    this.errorMessage = errorCode;
    this.errorCode = errorCode;
    this.subErrors = apiSubErrorList;
    httpStatus = null;
  }

  /**
   * Constructor with all fields
   *
   * @param message a message for parent exception
   * @param cause a cause exception
   * @param errorCode unique error code for failure
   * @param errorMessage error message for failure
   * @param httpStatus http status of exception
   * @param subErrors sub errors if exist
   */
  public BaseServiceException(
      String message,
      Throwable cause,
      String errorCode,
      String errorMessage,
      HttpStatus httpStatus,
      List<SubError> subErrors) {
    super(message, cause);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.httpStatus = httpStatus;
    this.subErrors = subErrors;
  }

  /**
   * Constructor with message fields
   *
   * @param message a message for parent exception
   * @param errorCode unique error code for failure
   * @param errorMessage error message for failure
   */
  public BaseServiceException(String message, String errorCode, String errorMessage) {
    super(message);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.httpStatus = null;
    this.subErrors = Collections.emptyList();
  }

  /**
   * Get error message
   *
   * @return error message
   */
  public String getErrorMessage() {
    return errorMessage;
  }

  /**
   * Get sub errors
   *
   * @return list of sub errors
   */
  public List<SubError> getSubErrors() {
    return subErrors;
  }

  /**
   * Get error code
   *
   * @return error code
   */
  public String getErrorCode() {
    return errorCode;
  }

  /**
   * Get http status of error
   *
   * @return http status
   */
  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
