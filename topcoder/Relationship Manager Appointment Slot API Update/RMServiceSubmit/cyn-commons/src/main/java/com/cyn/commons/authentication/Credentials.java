package com.cyn.commons.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Credentials data for login operation */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Credentials {

  /** Authentication method identifier * */
  private String authenticationMethodCode;

  /** Channel identifier * */
  private String channelIdentifier;

  /** Username * */
  private String userName;

  /** Password * */
  private String password;

  /** Otp code for O-OTP flow * */
  private String otp;
}
