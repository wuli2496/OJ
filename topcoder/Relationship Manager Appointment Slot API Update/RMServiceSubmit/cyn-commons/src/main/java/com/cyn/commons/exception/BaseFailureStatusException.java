package com.cyn.commons.exception;

import java.util.List;

/** A base class for exceptions that is successful from business perspective. */
public class BaseFailureStatusException extends BaseServiceException {

  /** Serial version of class. */
  private static final long serialVersionUID = 3760929608135446240L;

  /**
   * Crete an error with an error code and sub errors if exist
   *
   * @param errorCode the error key that will be used for reading message from message.properties
   * @param apiSubErrorList the list of sub errors if exist
   */
  public BaseFailureStatusException(String errorCode, List<SubError> apiSubErrorList) {
    super(errorCode, apiSubErrorList);
  }
}
