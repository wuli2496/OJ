package com.cyn.commons.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Introspection values for an access token */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IntrospectResponse {

  /** Nbf of the token */
  private long nbf;

  /** Scope of the token */
  private String scope;

  /** Active flag */
  private boolean active;

  /** Type of the token */
  private String tokenType;

  /** Expiry of the token */
  private long exp;

  /** Issue time of the token */
  private long iat;

  /** Client that requests the token */
  private String clientId;

  /** Username of the token */
  private String username;

  /** Customer unique id that user linked to */
  private String customerUniqueIdentifier;

  /** Email of customer */
  private String email;
}
