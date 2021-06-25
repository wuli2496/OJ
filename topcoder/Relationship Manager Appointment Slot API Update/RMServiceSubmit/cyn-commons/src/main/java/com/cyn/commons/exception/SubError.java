package com.cyn.commons.exception;

import java.io.Serializable;

/** Sub error structure to provide more information about error */
public class SubError implements Serializable {

  /** Serial version of the class * */
  private static final long serialVersionUID = 3403083467414438900L;

  /** Code of sub error * */
  private String errorCode;

  /**
   * Create a new sub error
   *
   * @param errorCode error code
   */
  public SubError(String errorCode) {
    this.errorCode = errorCode;
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
   * Set error code
   *
   * @param errorCode error code
   */
  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }
}
