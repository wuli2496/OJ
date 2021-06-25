package com.cyn.ccds.event.rm.appointment.client;

import java.time.ZonedDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO for Appointment */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {
  /** The ID */
  private long id;

  /** Appointment subject. */
  private String subject;

  /** Appointment description. */
  private String description;

  /** Appointment date. */
  private ZonedDateTime appointmentDate;

  /** Appointment user name. */
  private String userName;

  /** Appointment manager name. */
  private String managerName;

  /** Appointment mode. */
  private String mode;

  /** Appointment status. */
  private String status;

  /** Appointment address. */
  private String address;

  /** Appointment cancellation reason. */
  private String cancelationReason;

  /** Appointment new date. */
  private ZonedDateTime newDate;

  /** List of attachments */
  private List<AttachmentDTO> attachments;
}
