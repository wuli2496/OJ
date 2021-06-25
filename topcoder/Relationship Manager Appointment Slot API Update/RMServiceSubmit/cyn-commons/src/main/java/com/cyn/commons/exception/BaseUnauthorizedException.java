package com.cyn.commons.exception;

import java.util.List;

/** Exception to be thrown when request is unauthorized */
public class BaseUnauthorizedException extends BaseServiceException {

  /** Serial version of class. */
  private static final long serialVersionUID = 1461807410543814581L;

  /**
   * Constructor for unauthorized exceptions
   *
   * @param errorCode error code that will be used for looking up messages from bundle
   * @param apiSubErrorList error sub errors causing the problem
   */
  public BaseUnauthorizedException(String errorCode, List<SubError> apiSubErrorList) {
    super(errorCode, apiSubErrorList);
  }
}
