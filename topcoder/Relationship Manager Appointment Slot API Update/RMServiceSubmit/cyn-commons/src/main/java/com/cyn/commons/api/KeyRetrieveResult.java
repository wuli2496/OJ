package com.cyn.commons.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Result POJO for AEKM Key Retrieve response */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeyRetrieveResult {

  /** Base 64 encoded public or private key * */
  private String base64Key;
}
