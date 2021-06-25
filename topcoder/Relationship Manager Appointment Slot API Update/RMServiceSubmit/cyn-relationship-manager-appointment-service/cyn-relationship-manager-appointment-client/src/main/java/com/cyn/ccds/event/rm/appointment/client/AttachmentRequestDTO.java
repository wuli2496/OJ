package com.cyn.ccds.event.rm.appointment.client;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO carrying details of an attachment create request */
@NoArgsConstructor
@Data
public class AttachmentRequestDTO {

  /** Attachment file name */
  @NotNull private String fileName;

  /** Attachment file content (Base64 encoded) */
  @NotNull private String content;
}
