package com.cyn.commons.exception;

import java.util.List;

/** A base class for exceptions that are caused by invalid parameters */
public class BaseBadRequestException extends BaseServiceException {

  private static final long serialVersionUID = 6912820147800669637L;

  /**
   * @param errorCode
   * @param apiSubErrorList
   */
  public BaseBadRequestException(String errorCode, List<SubError> apiSubErrorList) {
    super(errorCode, apiSubErrorList);
  }
}
