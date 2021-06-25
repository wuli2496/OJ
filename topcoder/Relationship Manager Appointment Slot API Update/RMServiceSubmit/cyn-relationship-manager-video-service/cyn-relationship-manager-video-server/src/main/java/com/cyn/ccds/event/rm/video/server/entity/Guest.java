package com.cyn.ccds.event.rm.video.server.entity;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/** Relationship manager guest. */
@Entity
@Table(name = "Guests")
@Data
@Builder
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Guest {

  /** Id */
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  /** Guest id */
  @Column(name = "guest_id", columnDefinition = "VARCHAR(128)", nullable = false)
  private String guestId;

  /** Guest token. */
  @Column(name = "token", columnDefinition = "VARCHAR(128)", nullable = true)
  private String token;

  /** Expiration time. */
  @Column(name = "expiration_time", nullable = true)
  private ZonedDateTime expirationTime;

  /** Guest status */
  @Column(name = "status", columnDefinition = "VARCHAR(64) DEFAULT 'Pending'", nullable = false)
  private String status;

  /** Id session to attend */
  @ManyToOne
  @JoinColumn(name = "session_id", nullable = false)
  private AppointmentSession session;
}
