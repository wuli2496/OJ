package com.cyn.commons.filter;

import org.springframework.security.core.AuthenticationException;

/** Auth exception. */
public class AuthException extends AuthenticationException {

  static final long serialVersionUID = -7087897190745766939L;

  /** Auth error code. */
  private final String code;

  /**
   * Create a new spring authentication exception with error code
   *
   * @param code error code
   */
  public AuthException(String code) {
    super(code);
    this.code = code;
  }

  /**
   * Create a new spring authentication exception with error code and root cause
   *
   * @param code error code
   * @param t exception
   */
  public AuthException(String code, Throwable t) {
    super(code, t);
    this.code = code;
  }

  /**
   * Get auth error code
   *
   * @return error code
   */
  public String getCode() {
    return code;
  }
}
