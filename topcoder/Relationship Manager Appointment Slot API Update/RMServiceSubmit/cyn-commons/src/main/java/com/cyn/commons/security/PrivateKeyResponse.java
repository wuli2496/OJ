package com.cyn.commons.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Private key response. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrivateKeyResponse {

  /** App key. */
  private AppKey appKey;

  /** Private key. */
  private String privateKey;
}
