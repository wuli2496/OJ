package com.cyn.ccds.event.rm.appointment.client;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO for an appointment attachment */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentDTO {

  /** Id. */
  private long id;

  /** Attachment file url */
  private String url;

  /** Attachment file name */
  private String fileName;

  /** Attachment appointment */
  private long appointmentId;

  /** Created date. */
  private ZonedDateTime cratedDate;
}
