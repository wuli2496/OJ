package com.cyn.ccds.event.rm.video.server.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Relationship Manager guest DTO */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {

  /** Appointment against which sessions created. */
  private long appointmentId;

  /** Appointment creator user name */
  private String userName;

  /** Appointment manager name */
  private String managerName;
}
