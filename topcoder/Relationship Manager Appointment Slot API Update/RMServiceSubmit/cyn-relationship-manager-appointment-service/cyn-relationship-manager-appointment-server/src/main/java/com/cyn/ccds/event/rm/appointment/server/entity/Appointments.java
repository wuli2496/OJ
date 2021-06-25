package com.cyn.ccds.event.rm.appointment.server.entity;

import java.time.ZonedDateTime;
import java.util.List;
import javax.persistence.*;
import lombok.*;

/** Relationship manager video appointments. */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointments")
@Data
@ToString(callSuper = true)
public class Appointments {

  /** default sort column */
  public static final String DEFAULT_SORT_COLUMN = "appointmentDate";

  /** Id. */
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  /** Appointment subject. */
  @Column(name = "subject", columnDefinition = "VARCHAR(1024)", nullable = false)
  private String subject;

  /** Appointment description. */
  @Column(name = "description", columnDefinition = "TEXT", nullable = false)
  private String description;

  /** Appointment date. */
  @Column(name = "appointment_date", nullable = false)
  private ZonedDateTime appointmentDate;

  /** Appointment user name. */
  @Column(name = "user_name", nullable = false, columnDefinition = "VARCHAR(64)")
  private String userName;

  /** Appointment manager name. */
  @Column(name = "manager_name", nullable = false, columnDefinition = "VARCHAR(64)")
  private String managerName;

  /** Appointment mode. */
  @Column(name = "mode", columnDefinition = "VARCHAR(32)", nullable = false)
  private String mode;

  /** Appointment status. */
  @Column(name = "status", columnDefinition = "VARCHAR(32)", nullable = false)
  private String status;

  /** Appointment address. */
  @Column(name = "address", columnDefinition = "VARCHAR(1024)")
  private String address;

  /** Appointment cancellation reason. */
  @Column(name = "cancelation_reason", columnDefinition = "VARCHAR(1024)")
  private String cancelationReason;

  /** Appointment new date. */
  @Column(name = "new_date")
  private ZonedDateTime newDate;

  /** Appointment's attachments */
  @OneToMany(mappedBy = "appointment", fetch = FetchType.EAGER)
  private List<AppointmentAttachments> attachments;

  /** Microsoft graph event id associated with this appointment */
  @Column(name = "microsoft_graph_event_id", columnDefinition = "VARCHAR(200)")
  private String microsoftGraphEventId;
}
