package com.cyn.commons.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Structure to hold access token and id token for security context. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenPair {

  /** WSO2 access token; */
  private String accessToken;

  /** WSO2 id token; */
  private String idToken;
}
