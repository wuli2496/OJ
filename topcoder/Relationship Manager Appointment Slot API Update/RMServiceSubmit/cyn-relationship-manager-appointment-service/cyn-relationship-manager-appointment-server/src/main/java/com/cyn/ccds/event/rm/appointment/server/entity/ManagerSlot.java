package com.cyn.ccds.event.rm.appointment.server.entity;

import javax.persistence.*;
import lombok.*;

/** Entity class for ManagerSlot */
@Builder
@Entity
@Table(name = "manager_slots")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class ManagerSlot {

  /** Id. */
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  /** Start time of manager slot */
  @Column(name = "start_time", nullable = false)
  private int startTime;

  /** End time of manager slot */
  @Column(name = "end_time", nullable = false)
  private int endTime;

  /** Name of the manager */
  @Column(name = "manager_name", nullable = false)
  private String managerName;
}
