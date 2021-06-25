package com.cyn.commons.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Public key response. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicKeyResponse {

  /** App key. */
  private AppKey appKey;

  /** Public key. */
  private String publicKey;
}
