package com.cyn.ccds.event.rm.appointment.client;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Manager ratings DTO */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerRatingDTO {

  /** Id of the row */
  private long id;

  /** Rating Manager Name */
  private String managerName;

  /** Rating user name. */
  private String userName;

  /** Rating value */
  private int rating;

  /** Rating feedback. */
  private String feedback;

  /** Rating created date */
  private ZonedDateTime createdDate;
}
