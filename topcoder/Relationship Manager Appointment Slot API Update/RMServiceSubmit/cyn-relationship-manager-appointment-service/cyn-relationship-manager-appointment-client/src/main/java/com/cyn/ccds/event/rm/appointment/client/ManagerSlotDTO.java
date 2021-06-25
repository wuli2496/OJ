package com.cyn.ccds.event.rm.appointment.client;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Manager slot DTO */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerSlotDTO {

  /** Id of the row */
  private long id;

  /** Start time of manager slot in secs */
  @NotNull
  @Min(value = 0)
  @Max(value = 86400)
  private int startTime;

  /** End time of manager slot in secs */
  @NotNull
  @Max(value = 86400)
  private int endTime;

  /** Manager name */
  @NotNull private String managerName;
}
