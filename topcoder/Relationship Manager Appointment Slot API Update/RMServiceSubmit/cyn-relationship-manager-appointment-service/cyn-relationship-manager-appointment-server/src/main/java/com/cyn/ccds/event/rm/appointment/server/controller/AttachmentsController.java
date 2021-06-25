package com.cyn.ccds.event.rm.appointment.server.controller;

import com.cyn.ccds.event.rm.appointment.client.AttachmentDTO;
import com.cyn.ccds.event.rm.appointment.client.AttachmentRequestDTO;
import com.cyn.ccds.event.rm.appointment.server.service.AttachmentsService;
import com.cyn.commons.api.GenericResponse;
import com.cyn.commons.security.PayloadDecryption;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/** Appointments attachment controller. */
@RequestMapping("/attachments")
@RestController
public class AttachmentsController {

  /** Auto injected AttachmentsService instance */
  @Autowired AttachmentsService service;

  /**
   * Creates an appointment's attachment
   *
   * @param userName logged in user
   * @param appointmentId ID of the appointment
   * @param requestDTO uploaded file
   * @return created Appointment
   */
  @PayloadDecryption
  @PostMapping("/appointments/{appointmentId}")
  public GenericResponse<AttachmentDTO> createNewAttachment(
      @AuthenticationPrincipal String userName,
      @PathVariable("appointmentId") long appointmentId,
      @Valid @RequestBody AttachmentRequestDTO requestDTO) {
    return new GenericResponse<>(service.createNewAttachment(appointmentId, requestDTO, userName));
  }

  /**
   * Get appointment attachment by its ID
   *
   * @param userName username of the logged-in user
   * @param attachmentId ID of the rating
   * @return found appointment attachment
   */
  @PayloadDecryption
  @GetMapping("/{attachmentId}")
  public GenericResponse<AttachmentDTO> getAttachmentById(
      @AuthenticationPrincipal String userName, @PathVariable("attachmentId") long attachmentId) {
    return new GenericResponse<>(service.findById(attachmentId, userName));
  }

  /**
   * @param userName username of the logged-in user
   * @param attachmentId ID of the attachment to be deleted
   */
  @PayloadDecryption
  @DeleteMapping("/{attachmentId}")
  public GenericResponse deleteAttachmentById(
      @AuthenticationPrincipal String userName, @PathVariable("attachmentId") long attachmentId) {
    service.deleteAttachment(attachmentId, userName);
    return new GenericResponse();
  }
}
