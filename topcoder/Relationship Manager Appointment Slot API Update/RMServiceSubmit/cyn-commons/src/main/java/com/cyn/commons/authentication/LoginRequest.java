package com.cyn.commons.authentication;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Main Login Request POJO */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

  /** Flag to perform multiCredentialsValidation * */
  private boolean multiCredentialsValidation;

  /** Mfa Event * */
  private MfaEvent mfaEvent;

  /** Credentials containing username and password * */
  @NotNull private Credentials credentials;

  /** GeoLocation or login caller * */
  private GeoLocation geoLocation;

  /** Device Identifier. */
  private String deviceIdentifier;

  /** Device operating system. */
  private String deviceOs;
}
