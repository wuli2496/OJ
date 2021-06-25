package com.cyn.ccds.event.rm.appointment.client;

import java.time.ZonedDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Object carrying appointment create/update appointment details */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestDTO {

  /** List of manager slot Ids */
  @NotNull private List<Long> managerSlotIds;

  /** Appointment subject. */
  @NotNull private String subject;

  /** Appointment description. */
  private String description;

  /** Appointment date. */
  @NotNull private ZonedDateTime appointmentDate;

  /** Appointment mode. */
  @NotNull private String mode;

  /** Appointment address. */
  private String address;
}
