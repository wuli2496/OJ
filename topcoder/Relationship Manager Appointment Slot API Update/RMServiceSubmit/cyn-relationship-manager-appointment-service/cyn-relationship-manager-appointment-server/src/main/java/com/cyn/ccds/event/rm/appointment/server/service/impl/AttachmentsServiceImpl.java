package com.cyn.ccds.event.rm.appointment.server.service.impl;

import com.cyn.ccds.event.rm.appointment.client.AttachmentDTO;
import com.cyn.ccds.event.rm.appointment.client.AttachmentRequestDTO;
import com.cyn.ccds.event.rm.appointment.server.entity.AppointmentAttachments;
import com.cyn.ccds.event.rm.appointment.server.entity.Appointments;
import com.cyn.ccds.event.rm.appointment.server.exception.ErrorCode;
import com.cyn.ccds.event.rm.appointment.server.repository.AppointmentAttachmentsRepository;
import com.cyn.ccds.event.rm.appointment.server.repository.AppointmentsRepository;
import com.cyn.ccds.event.rm.appointment.server.service.AttachmentsService;
import com.cyn.ccds.event.rm.appointment.server.service.StorageService;
import com.cyn.commons.exception.BaseNotFoundException;
import com.cyn.commons.exception.BaseServiceException;
import com.google.api.client.util.Base64;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Implementation class for {@link AttachmentsService} */
@Service
public class AttachmentsServiceImpl implements AttachmentsService {

  /** Auto injected AppointmentAttachmentsRepository instance */
  @Autowired private AppointmentAttachmentsRepository attachmentsRepo;

  /** Auto injected AppointmentsRepository instance */
  @Autowired private AppointmentsRepository appointmentsRepo;

  /** Auto injected StorageService instance */
  @Autowired private StorageService storageService;

  /**
   * Create new appointment attachment
   *
   * @param appointmentId Id of the appointment uploaded attachments belong to
   * @param requestDTO request object containing attachment details
   * @param userName logged in userName
   * @return created attachment
   */
  @Override
  public AttachmentDTO createNewAttachment(
      long appointmentId, AttachmentRequestDTO requestDTO, String userName) {
    Appointments appointments = validateAndGetAppointment(appointmentId, userName);
    String fileName = requestDTO.getFileName();
    try {
      byte[] data = Base64.decodeBase64(requestDTO.getContent());
      String url = storageService.storeFile(appointmentId, fileName, data);
      AppointmentAttachments attachments =
          AppointmentAttachments.builder()
              .url(url)
              .fileName(fileName)
              .appointment(appointments)
              .cratedDate(ZonedDateTime.now())
              .build();
      attachmentsRepo.save(attachments);
      return getFromAttachment(attachments);
    } catch (Exception e) {
      throw new BaseServiceException(ErrorCode.RMA_ERR000011.name(), Collections.emptyList());
    }
  }

  /**
   * Find attachment by its ID
   *
   * @param attachmentId ID to be found
   * @return found Attachment
   */
  @Override
  public AttachmentDTO findById(long attachmentId, String userName) {
    return getFromAttachment(getAttachmentById(attachmentId, userName, false));
  }

  /**
   * Delete an attachment
   *
   * @param attachmentId ID of the attachment to be deleted
   * @param userName logged in userName
   */
  @Override
  public void deleteAttachment(long attachmentId, String userName) {
    AppointmentAttachments attachment = getAttachmentById(attachmentId, userName, true);
    storageService.deleteFile(attachment.getAppointment().getId(), attachment.getFileName());
    attachmentsRepo.delete(attachment);
  }

  /**
   * Find Appointments by Id, and throw exception if not found or not authorised
   *
   * @param id ID of the row to be found
   * @return Found Appointments
   */
  private Appointments validateAndGetAppointment(Long id, String userName) {
    Optional<Appointments> found = appointmentsRepo.findById(id);

    if (!found.isPresent()) {
      throw new BaseNotFoundException(ErrorCode.RMA_ERR000004.name(), Collections.emptyList());
    }
    Appointments appointments = found.get();

    if (!appointments.getUserName().equalsIgnoreCase(userName)) {
      throw new BaseNotFoundException(ErrorCode.RMA_ERR000009.name(), Collections.emptyList());
    }
    return appointments;
  }

  /**
   * Get AttachmentDTO created out of AppointmentAttachments
   *
   * @param attachments the entity instance
   * @return the populated DTO
   */
  private AttachmentDTO getFromAttachment(AppointmentAttachments attachments) {
    return AttachmentDTO.builder()
        .id(attachments.getId())
        .appointmentId(attachments.getAppointment().getId())
        .fileName(attachments.getFileName())
        .url(attachments.getUrl())
        .build();
  }

  /**
   * Validate and get AppointmentAttachments by ID. Throws exception if object couldn't be found or
   * not authorised
   *
   * @param attachmentId ID of the AppointmentAttachments
   * @param userName the logged-in userName
   * @param checkOwnership whether to check if appointment is for the logged-in user or not
   * @return AppointmentAttachments found by ID
   */
  private AppointmentAttachments getAttachmentById(
      long attachmentId, String userName, boolean checkOwnership) {
    Optional<AppointmentAttachments> found = attachmentsRepo.findById(attachmentId);
    if (found.isEmpty()) {
      throw new BaseNotFoundException(ErrorCode.RMA_ERR000004.name(), Collections.emptyList());
    }
    AppointmentAttachments attachments = found.get();
    if (checkOwnership && !attachments.getAppointment().getUserName().equalsIgnoreCase(userName)) {
      throw new BaseNotFoundException(ErrorCode.RMA_ERR000009.name(), Collections.emptyList());
    }
    return attachments;
  }
}
