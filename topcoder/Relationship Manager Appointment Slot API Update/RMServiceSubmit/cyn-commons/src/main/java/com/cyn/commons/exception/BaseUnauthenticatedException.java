package com.cyn.commons.exception;

import java.util.Collections;
import java.util.List;

/** Exception to be thrown when request is unauthenticated */
public class BaseUnauthenticatedException extends BaseServiceException {

  /** Serial version of class. */
  private static final long serialVersionUID = 4169306844219075855L;

  /**
   * Constructor for unauthenticated exceptions
   *
   * @param errorCode error code that will be used for looking up messages from bundle
   * @param apiSubErrorList error sub errors causing the problem
   */
  public BaseUnauthenticatedException(String errorCode, List<SubError> apiSubErrorList) {
    super(errorCode, apiSubErrorList);
  }

  /**
   * Constructor for unauthenticated exceptions
   *
   * @param errorCode error code that will be used for looking up messages from bundle
   */
  public BaseUnauthenticatedException(String errorCode) {
    super(errorCode, Collections.emptyList());
  }

  /**
   * Constructor for unauthenticated exceptions
   *
   * @param message short error message.
   * @param errorCode error code that will be used for looking up messages from bundle
   * @param errorMessage detailed error message
   */
  public BaseUnauthenticatedException(String message, String errorCode, String errorMessage) {
    super(message, errorCode, errorMessage);
  }
}
