package com.cyn.ccds.event.rm.appointment.server.entity;

import java.time.ZonedDateTime;
import javax.persistence.*;
import lombok.*;

/** Manager ratings. */
@Entity
@Table(name = "manager_ratings")
@Data
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerRatings {

  /** Default sort column */
  public static final String DEFAULT_SORT_COLUMN = "createdDate";

  /** Id. */
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  /** Rating Manager Name */
  @Column(name = "manager_name", columnDefinition = "VARCHAR(64)", nullable = false)
  private String managerName;

  /** Rating user name. */
  @Column(name = "user_name", columnDefinition = "VARCHAR(64)", nullable = false)
  private String userName;

  /** Rating value */
  @Column(name = "rating", columnDefinition = "SMALLINT", nullable = false)
  private int rating;

  /** Rating feedback. */
  @Column(name = "feedback", columnDefinition = "TEXT", nullable = false)
  private String feedback;

  @Column(name = "created_date", nullable = false)
  private ZonedDateTime createdDate;
}
