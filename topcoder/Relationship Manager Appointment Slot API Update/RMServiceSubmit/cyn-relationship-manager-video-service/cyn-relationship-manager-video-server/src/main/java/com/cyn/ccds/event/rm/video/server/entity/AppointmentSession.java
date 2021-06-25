package com.cyn.ccds.event.rm.video.server.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString;

/** Relationship manager video sessions. */
@Entity
@Table(name = "sessions")
@Data
@ToString(callSuper = true)
public class AppointmentSession {

  /** Id. */
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  /** RM video session id. */
  @Column(name = "session_id", columnDefinition = "VARCHAR(128)", nullable = false)
  private String sessionId;

  /** RM video appointment. */
  @Column(name = "appointments_id", columnDefinition = "BIGINT", nullable = false)
  private long appointmentId;

  @Column(name = "user_name", columnDefinition = "VARCHAR(64)", nullable = false)
  private String userName;

  /** Appointment Manager name */
  @Column(name = "manager_name", columnDefinition = "VARCHAR(64)", nullable = false)
  private String managerName;

  /** Max number of available guests for video session */
  @Column(name = "max_guests", columnDefinition = "INTEGER DEFAULT 6", nullable = false)
  private int maxGuests;

  /** Reference to sessions guests */
  @OneToMany(mappedBy = "session")
  private Set<Guest> guests;
}
