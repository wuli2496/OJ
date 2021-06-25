package com.cyn.commons.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Result POJO for Key Exchange response */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeyExchangeResult {

  /** The status of key exchange operation * */
  private String status;

  /** Base 64 encoded public or private key * */
  private String base64Key;
}
