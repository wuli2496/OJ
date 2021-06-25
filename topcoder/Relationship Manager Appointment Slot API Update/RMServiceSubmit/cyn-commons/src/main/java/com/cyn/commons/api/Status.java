package com.cyn.commons.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The status object to contain details about error. Only code will be filled for success case. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Status {
  /** Api Code of response * */
  private String apiCode;

  /** Code of response * */
  private ResponseCode code;

  /** Error Code of response * */
  private String errorCode;

  /** Internal Message of response * */
  private String internalMessage;

  /** Message of response * */
  private String message;
}
