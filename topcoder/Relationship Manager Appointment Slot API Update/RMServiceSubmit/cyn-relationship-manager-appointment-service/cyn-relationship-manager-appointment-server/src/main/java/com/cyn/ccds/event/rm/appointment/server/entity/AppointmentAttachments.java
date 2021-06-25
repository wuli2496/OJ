package com.cyn.ccds.event.rm.appointment.server.entity;

import java.time.ZonedDateTime;
import javax.persistence.*;
import lombok.*;

/** Entity class for an Appointment's attachment */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointment_attachments")
@Data
@ToString(callSuper = true)
public class AppointmentAttachments {

  /** Id. */
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  /** Attachment file url */
  @Column(name = "url", columnDefinition = "VARCHAR(2048)", nullable = false)
  private String url;

  /** Attachment file name */
  @Column(name = "file_name", columnDefinition = "VARCHAR(128)", nullable = false)
  private String fileName;

  /** Attachment appointment */
  @ManyToOne
  @JoinColumn(name = "appointment_id", nullable = false)
  private Appointments appointment;

  /** Created date. */
  @Column(name = "created_date", nullable = false)
  private ZonedDateTime cratedDate;
}
