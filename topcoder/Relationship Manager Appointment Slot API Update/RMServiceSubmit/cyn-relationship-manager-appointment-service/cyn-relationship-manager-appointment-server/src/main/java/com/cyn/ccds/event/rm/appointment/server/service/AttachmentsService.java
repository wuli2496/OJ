package com.cyn.ccds.event.rm.appointment.server.service;

import com.cyn.ccds.event.rm.appointment.client.AttachmentDTO;
import com.cyn.ccds.event.rm.appointment.client.AttachmentRequestDTO;

/** Service interface for AppointmentAttachments */
public interface AttachmentsService {

  /**
   * Create new appointment attachment
   *
   * @param appointmentId Id of the appointment uploaded attachments belong to
   * @param requestDTO request object containing attachment details
   * @param userName logged in userName
   * @return created attachment
   */
  AttachmentDTO createNewAttachment(
      long appointmentId, AttachmentRequestDTO requestDTO, String userName);

  /**
   * Find attachment by its ID
   *
   * @param attachmentId ID to be found
   * @return found Attachment
   */
  AttachmentDTO findById(long attachmentId, String userName);

  /**
   * Delete an attachment
   *
   * @param attachmentId ID of the attachment to be deleted
   * @param userName logged in userName
   */
  void deleteAttachment(long attachmentId, String userName);
}
