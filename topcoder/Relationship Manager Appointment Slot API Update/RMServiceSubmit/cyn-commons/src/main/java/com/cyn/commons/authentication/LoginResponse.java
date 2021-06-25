package com.cyn.commons.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Main Login Response POJO */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

  /** Access token for user * */
  @JsonProperty(value = "access_token")
  private String accessToken;

  /** Refresh token for user * */
  @JsonProperty(value = "refresh_token")
  private String refreshToken;

  /** Scope of the token * */
  private String scope;

  /** Id token for user * */
  @JsonProperty(value = "id_token")
  private String idToken;

  /** Type of the token * */
  @JsonProperty(value = "token_type")
  private String tokenType;

  /** Expiry date for access token * */
  @JsonProperty(value = "access_token_expires_in")
  private Long accessTokenExpiresIn;

  /** Expiry date for refresh token * */
  @JsonProperty(value = "refresh_token_expires_in")
  private Long refreshTokenExpiresIn;

  /** Redirection required */
  private boolean redirectionRequired;

  /** Redirect location */
  private String location;
}
