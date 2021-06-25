package com.cyn.commons.exception;

import java.util.List;

/** A base class for exceptions that are caused by security errors occurs during api to to call */
public class BaseClientEncryptionException extends BaseServiceException {

  private static final long serialVersionUID = -2647664162256541857L;

  /**
   * @param errorCode
   * @param apiSubErrorList
   */
  public BaseClientEncryptionException(String errorCode, List<SubError> apiSubErrorList) {
    super(errorCode, apiSubErrorList);
  }
}
