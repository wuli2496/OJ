package com.cyn.commons.api;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Dead letter queue message for failed operation */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DLQMessage {
  /** Component name * */
  private String componentName;

  /** Reason of failure. * */
  private String failureReason;

  /** Code of the failure. * */
  private String failureCode;

  /** Original payload. * */
  private Object payload;

  /** Datetime the failure. * */
  private ZonedDateTime dateTime;
}
