package com.cyn.ccds.event.rm.appointment.server.entity;

import javax.persistence.*;
import lombok.*;

/** Entity class for ManagerAppointments */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "manager_appointments")
@Data
@ToString(callSuper = true)
public class ManagerAppointments {

  /** Id. */
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  /** Manager slot id */
  @Column(name = "manager_slot_id", nullable = false)
  private long managerSlotId;

  /** manager appointment's appointment */
  @ManyToOne private Appointments appointments;
}
