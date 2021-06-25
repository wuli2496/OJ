package com.cyn.commons.authentication;

/** Enum for login mfa event. */
public enum MfaEvent {

  /** One span push notification. */
  O_PUSH("O-PUSH"),

  /** One span otp. */
  O_OTP("O-OTP");

  /** Real value of the enum. */
  private final String value;

  /**
   * Constructor of the enum.
   *
   * @param value
   */
  MfaEvent(String value) {
    this.value = value;
  }

  /**
   * Return the actual value of enum.
   *
   * @return actual value.
   */
  public String getValue() {
    return value;
  }
}
