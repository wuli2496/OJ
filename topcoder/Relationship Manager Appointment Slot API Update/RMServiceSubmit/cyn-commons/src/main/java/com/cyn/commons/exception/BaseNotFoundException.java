package com.cyn.commons.exception;

import java.util.List;

/** A base class for entity not found exceptions to be thrown in business layer. */
public class BaseNotFoundException extends BaseServiceException {

  /** Serial version of class */
  private static final long serialVersionUID = -3223313382463667810L;

  /**
   * Crete an error with an error code and sub errors if exist
   *
   * @param errorCode the error key that will be used for reading message from message.properties
   * @param apiSubErrorList the list of sub errors if exist
   */
  public BaseNotFoundException(String errorCode, List<SubError> apiSubErrorList) {
    super(errorCode, apiSubErrorList);
  }
}
