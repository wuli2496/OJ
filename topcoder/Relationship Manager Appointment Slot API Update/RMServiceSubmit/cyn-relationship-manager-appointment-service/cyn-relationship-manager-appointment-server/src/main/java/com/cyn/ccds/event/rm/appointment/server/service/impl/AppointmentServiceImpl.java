package com.cyn.ccds.event.rm.appointment.server.service.impl;

import com.cyn.ccds.event.rm.appointment.client.AppointmentDTO;
import com.cyn.ccds.event.rm.appointment.client.AppointmentRequestDTO;
import com.cyn.ccds.event.rm.appointment.client.AttachmentDTO;
import com.cyn.ccds.event.rm.appointment.client.PaginatedList;
import com.cyn.ccds.event.rm.appointment.server.entity.AppointmentStatus;
import com.cyn.ccds.event.rm.appointment.server.entity.Appointments;
import com.cyn.ccds.event.rm.appointment.server.entity.ManagerAppointments;
import com.cyn.ccds.event.rm.appointment.server.entity.ManagerSlot;
import com.cyn.ccds.event.rm.appointment.server.exception.ErrorCode;
import com.cyn.ccds.event.rm.appointment.server.repository.AppointmentsRepository;
import com.cyn.ccds.event.rm.appointment.server.repository.ManagerAppointmentsRepository;
import com.cyn.ccds.event.rm.appointment.server.repository.ManagerSlotsRepository;
import com.cyn.ccds.event.rm.appointment.server.service.AppointmentService;
import com.cyn.ccds.event.rm.appointment.server.service.MicrosoftGraphService;
import com.cyn.ccds.event.rm.appointment.server.service.VideoServiceInvoker;
import com.cyn.commons.exception.BaseBadRequestException;
import com.cyn.commons.exception.BaseNotFoundException;
import com.google.api.client.util.Lists;
import com.microsoft.graph.models.Event;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/** Implementation class for {@link AppointmentService} */
@RequiredArgsConstructor
@Slf4j
@Service
public class AppointmentServiceImpl implements AppointmentService {

  /** Max duration of an appointment is 2 hours */
  private static final int MAX_APPOINTMENT_DURATION = 2 * 60 * 60;

  /** Auto injected AppointmentRepository instance */
  private final AppointmentsRepository appointmentRepo;

  /** Auto injected AppointmentRepository instance */
  private final ManagerSlotsRepository slotRepo;

  /** Auto injected ManagerAppointments repository */
  private final ManagerAppointmentsRepository managerAppointmentsRepo;

  /** Auto injected VideoServiceInvoker instance */
  private final VideoServiceInvoker videoServiceInvoker;

  /** Auto injected MicrosoftGraphService instance */
  private final MicrosoftGraphService microsoftGraphService;

  /**
   * Find all appointments for the given user
   *
   * @param userName username name
   * @return Found list of user appointments
   */
  @Override
  public PaginatedList findAll(String userName, int skip, int limit) {
    validatePaginationParams(skip, limit);
    Sort order = Sort.by(Sort.Order.desc(Appointments.DEFAULT_SORT_COLUMN));
    Pageable pageRequest = PageRequest.of(skip / limit, limit, order);
    Page<Appointments> results =
        appointmentRepo.findByUserNameAndStatusNot(
            userName, AppointmentStatus.DELETED.getStatus(), pageRequest);

    Page<AppointmentDTO> page = results.map(rt -> getAppointmentDto(rt));
    return new PaginatedList(page.getTotalElements(), page.getContent());
  }

  /**
   * Create a new appointment
   *
   * @param request DTO appointment details to be created
   * @return ID of the created user appointment
   */
  @Override
  @Transactional
  public AppointmentDTO createAppointment(AppointmentRequestDTO request, String userName) {
    List<ManagerSlot> slots = validateRequestedSlots(request.getManagerSlotIds());

    checkSlotsClashingWithOthers(slots, request.getAppointmentDate(), null);

    String managerName = slots.get(0).getManagerName();
    int startTimeInSecs = slots.get(0).getStartTime();
    ZonedDateTime appointmentDate =
        getAppointmentDate(request.getAppointmentDate(), startTimeInSecs);

    Appointments app =
        Appointments.builder()
            .appointmentDate(appointmentDate)
            .address(request.getAddress())
            .managerName(managerName)
            .description(request.getDescription())
            .mode(request.getMode())
            .status(AppointmentStatus.PENDING.getStatus())
            .userName(userName)
            .subject(request.getSubject())
            .build();

    app = appointmentRepo.save(app);

    // create entries in manager_appointments
    createManagerAppointments(slots, app);

    // call video service to create a session
    String sessionId = videoServiceInvoker.createSession(app.getId(), managerName, userName);
    log.info("successfully created session with id: " + sessionId);

    try {
      Event event = microsoftGraphService.createEvent(app, slots);
      log.info("successfully created microsoft graph event with id {}", event.id);
      app.setMicrosoftGraphEventId(event.id);
      appointmentRepo.save(app);
    } catch (Exception e) {
      log.error("Failed to create calendar", e);
    }

    return getAppointmentDto(app);
  }

  /**
   * Find an appointment by its row ID
   *
   * @param id ID for which manager rating is to be found
   * @param userName logged in user name
   * @return Found appointment
   */
  @Override
  public AppointmentDTO findById(Long id, String userName) {
    return getAppointmentDto(validateAndGetAppointment(id, userName));
  }

  /**
   * Update an appointment
   *
   * @param id ID of the row that needs to be updated
   * @param request DTO carrying updated appointment details
   * @return updated appointment
   */
  @Override
  @Transactional
  public AppointmentDTO updateAppointment(Long id, AppointmentRequestDTO request, String userName) {
    Appointments appointment = validateAndGetAppointment(id, userName);
    List<ManagerSlot> slots = validateRequestedSlots(request.getManagerSlotIds());
    int startTimeInSecs = slots.get(0).getStartTime();
    ZonedDateTime appointmentDate =
        getAppointmentDate(request.getAppointmentDate(), startTimeInSecs);

    checkSlotsClashingWithOthers(slots, request.getAppointmentDate(), id);

    // Manager name, status can't be changed
    appointment.setAppointmentDate(appointmentDate);
    appointment.setAddress(request.getAddress());
    appointment.setDescription(request.getDescription());
    appointment.setMode(request.getMode());
    appointment.setSubject(request.getSubject());
    appointmentRepo.save(appointment);

    // also replace manager appointments slot with new slots
    List<ManagerAppointments> managerAppointments =
        managerAppointmentsRepo.findByAppointmentsId(appointment.getId());
    for (ManagerAppointments ma : managerAppointments) {
      managerAppointmentsRepo.delete(ma);
    }

    createManagerAppointments(slots, appointment);

    if (appointment.getMicrosoftGraphEventId() != null) {
      microsoftGraphService.updateEvent(appointment, slots);
      log.info("Updated microsoft graph event: {}", appointment.getMicrosoftGraphEventId());
    }

    return getAppointmentDto(appointment);
  }

  /**
   * Soft deletes an appointment by its row ID
   *
   * @param id ID for which manager slot is to be deleted
   * @param userName logged in user name
   */
  @Override
  @Transactional
  public void deleteAppointment(Long id, String userName) {
    Appointments appointment = validateAndGetAppointment(id, userName);
    appointment.setStatus(AppointmentStatus.DELETED.getStatus());
    appointmentRepo.save(appointment);

    if (appointment.getMicrosoftGraphEventId() != null) {
      microsoftGraphService.deleteEvent(appointment.getMicrosoftGraphEventId());
      log.info("Deleted microsoft graph event: {}", appointment.getMicrosoftGraphEventId());
    }
  }

  /**
   * Find Appointments by Id, and throw exception if not found or not authorised
   *
   * @param id ID of the row to be found
   * @return Found Appointments
   */
  private Appointments validateAndGetAppointment(Long id, String userName) {
    Optional<Appointments> found = appointmentRepo.findById(id);

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
   * Validate the appointment's requested slots slots should be consecutive, belonging to the same
   * manager and in total less than 2 hours
   *
   * @param managerSlotIds the requested slots
   * @return list of ManagerSlot entities if found valid
   */
  private List<ManagerSlot> validateRequestedSlots(List<Long> managerSlotIds) {
    List<ManagerSlot> managerSlots = Lists.newArrayList();
    ManagerSlot previous = null;
    long totalSlotDuration = 0L;

    // no slot info present
    if (managerSlotIds == null || managerSlotIds.size() == 0) {
      throw new BaseBadRequestException(ErrorCode.RMA_ERR000014.name(), Collections.emptyList());
    }

    // validate slots
    for (Long eachId : managerSlotIds) {
      Optional<ManagerSlot> slot = slotRepo.findById(eachId);

      // slot not found by id
      if (!slot.isPresent()) {
        throw new BaseNotFoundException(ErrorCode.RMA_ERR000004.name(), Collections.emptyList());
      }

      if (previous != null) {
        // multiple manager's slot requested
        if (!previous.getManagerName().equalsIgnoreCase(slot.get().getManagerName())) {
          throw new BaseBadRequestException(
              ErrorCode.RMA_ERR000008.name(), Collections.emptyList());
        }

        // non-consecutive slots
        if (previous.getEndTime() != slot.get().getStartTime()) {
          throw new BaseBadRequestException(
              ErrorCode.RMA_ERR000006.name(), Collections.emptyList());
        }
      }
      previous = slot.get();
      managerSlots.add(slot.get());
      totalSlotDuration += (previous.getEndTime() - previous.getStartTime());
    }

    // check total appointment duration
    if (totalSlotDuration > MAX_APPOINTMENT_DURATION) {
      throw new BaseBadRequestException(ErrorCode.RMA_ERR000007.name(), Collections.emptyList());
    }
    return managerSlots;
  }

  /**
   * Check if there exists any manager appointment with same requested appointment date
   *
   * @param requestedSlots list of requested slots
   * @param appointmentDate requested appointment date
   * @param thisAppointmentId current appointment id
   * @return true if there is another manager appointment booked for the same date and time
   */
  private void checkSlotsClashingWithOthers(
      List<ManagerSlot> requestedSlots, ZonedDateTime appointmentDate, Long thisAppointmentId) {

    for (ManagerSlot slot : requestedSlots) {
      ZonedDateTime slotStartTime = getAppointmentDate(appointmentDate, slot.getStartTime());
      List<ManagerAppointments> managerAppointments =
          managerAppointmentsRepo.findWithSameSlotIdAndDate(slot.getId(), slotStartTime);
      boolean otherFound = false;
      if (thisAppointmentId != null) {
        otherFound =
            managerAppointments.stream()
                .anyMatch(ma -> ma.getAppointments().getId() != thisAppointmentId);
      } else {
        otherFound = managerAppointments.size() > 0;
      }
      if (otherFound) {
        throw new BaseBadRequestException(ErrorCode.RMA_ERR000010.name(), Collections.emptyList());
      }
    }
  }

  /**
   * Create link between list of ManagerSlot and Appointment
   *
   * @param slots the Manager slots
   * @param app the appointment
   */
  private void createManagerAppointments(List<ManagerSlot> slots, Appointments app) {
    for (ManagerSlot slot : slots) {
      ManagerAppointments managerAppointments =
          ManagerAppointments.builder().managerSlotId(slot.getId()).appointments(app).build();
      managerAppointmentsRepo.save(managerAppointments);
    }
  }

  /**
   * Convert an Appointment into an AppointmentDTO
   *
   * @param app the appointment
   * @return the equivalent DTO
   */
  private AppointmentDTO getAppointmentDto(Appointments app) {
    AppointmentDTO appDto =
        AppointmentDTO.builder()
            .id(app.getId())
            .managerName(app.getManagerName())
            .userName(app.getUserName())
            .appointmentDate(app.getAppointmentDate())
            .address(app.getAddress())
            .cancelationReason(app.getCancelationReason())
            .description(app.getDescription())
            .mode(app.getMode())
            .newDate(app.getNewDate())
            .status(app.getStatus())
            .subject(app.getSubject())
            .build();

    List<AttachmentDTO> attachmentDtos =
        app.getAttachments() != null
            ? app.getAttachments().stream()
                .map(
                    att ->
                        AttachmentDTO.builder()
                            .id(att.getId())
                            .appointmentId(app.getId())
                            .url(att.getUrl())
                            .fileName(att.getFileName())
                            .cratedDate(att.getCratedDate())
                            .build())
                .collect(Collectors.toList())
            : Lists.newArrayList();

    appDto.setAttachments(attachmentDtos);
    return appDto;
  }

  /**
   * Get appointment time taking into consideration the requested date and first slot
   *
   * @param date the chosen appointment date (may or may not include time). It will also truncate to
   *     start of day time
   * @param slotStartTimeInSecs start time of the slot
   * @return Appointment start time (date + time)
   */
  private ZonedDateTime getAppointmentDate(ZonedDateTime date, int slotStartTimeInSecs) {
    ZonedDateTime zdt1 = date.truncatedTo(ChronoUnit.DAYS);
    ZonedDateTime actualTime = zdt1.plusSeconds(slotStartTimeInSecs);
    return actualTime;
  }

  /**
   * Validate the pagination params
   *
   * @param skip number of rows to skip
   * @param limit number of rows to return
   */
  private void validatePaginationParams(int skip, int limit) {
    if (skip < 0 || limit <= 0) {
      throw new BaseBadRequestException(ErrorCode.RMA_ERR000001.name(), Collections.emptyList());
    }
  }
}
