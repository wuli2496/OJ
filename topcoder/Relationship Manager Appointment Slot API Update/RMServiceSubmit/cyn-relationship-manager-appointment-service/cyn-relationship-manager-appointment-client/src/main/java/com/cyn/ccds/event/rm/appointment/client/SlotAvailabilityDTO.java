package com.cyn.ccds.event.rm.appointment.client;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO for a manager's slot availability on a particular date */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SlotAvailabilityDTO {
  /** Available status */
  public static final String AVAILABLE = "available";

  /** UnAvailable status */
  public static final String UNAVAILABLE = "unavailable";

  /** available check date */
  String date;

  /** list of manager's slot */
  List<SlotDTO> slots;

  /** DTO for a manager's slot info */
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  @Data
  public static class SlotDTO {
    /** Start time of slot (in secs) */
    private int startTime;

    /** End time of slot (in secs) */
    private int endTime;

    /** The availability statue */
    private String status;
  }
}
