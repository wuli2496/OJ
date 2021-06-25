package com.cyn.commons.security;

/** Standard set of headers for CYN SERVICES */
public class SecurityHeaderConstants {

  private SecurityHeaderConstants() {}

  /** App Name Header * */
  public static final String X_APP_HEADER = "x-app";

  /** App Guid * */
  public static final String X_GUID_HEADER = "x-guid";

  /** RSA Encrypted symmetric key header * */
  public static final String X_RHYTHM_HEADER = "x-rhythm";

  /** Cyn event header * */
  public static final String X_CYN_EVENT_HEADER = "x-cyn-event";

  /** Cyn Authorization header * */
  public static final String X_AUTHORIZATION_HEADER = "x-authorisation";

  /** Standard Authentication Header * */
  public static final String STANDARD_AUTHORIZATION_HEADER = "Authorization";

  /** Cyn id token * */
  public static final String ID_TOKEN_HEADER = "x-id-token";
}
