package com.cyn.commons.util;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/** Context POJO for JWE Generation */
@AllArgsConstructor
@Builder
@Data
public class JWEContext {
  /** Subject of the token */
  private final String subject;

  /** Claims of the token */
  private final Map<String, String> claims;

  /** Private key for JWE Generation */
  private final String encodedPrivateKey;

  /** Public key for Json Web Key Generation */
  private final String publicKeyCertificate;

  /** The public key of resource service */
  private final String providerCertificate;

  /** Expire time of the token */
  private final int tokenLifeInMinutes;

  /** Issuer of the token */
  private final String issuer;
}
