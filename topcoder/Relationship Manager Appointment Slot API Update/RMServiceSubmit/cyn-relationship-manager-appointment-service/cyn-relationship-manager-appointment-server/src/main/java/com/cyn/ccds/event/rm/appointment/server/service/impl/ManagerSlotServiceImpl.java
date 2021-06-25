package com.cyn.ccds.event.rm.appointment.server.service.impl;

import com.cyn.ccds.event.rm.appointment.client.ManagerSlotDTO;
import com.cyn.ccds.event.rm.appointment.client.SlotAvailabilityDTO;
import com.cyn.ccds.event.rm.appointment.server.entity.ManagerAppointments;
import com.cyn.ccds.event.rm.appointment.server.entity.ManagerSlot;
import com.cyn.ccds.event.rm.appointment.server.exception.ErrorCode;
import com.cyn.ccds.event.rm.appointment.server.repository.ManagerAppointmentsRepository;
import com.cyn.ccds.event.rm.appointment.server.repository.ManagerSlotsRepository;
import com.cyn.ccds.event.rm.appointment.server.service.ManagerSlotService;
import com.cyn.commons.exception.BaseBadRequestException;
import com.cyn.commons.exception.BaseNotFoundException;
import com.google.api.client.util.Lists;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Implementation class for {@link ManagerSlotService} */
@Slf4j
@Service
public class ManagerSlotServiceImpl implements ManagerSlotService {

  private static final String DB_DATE_FORMAT = "yyyy-MM-dd";

  /** Auto injected ManagerSlotRepository instance */
  @Autowired private ManagerSlotsRepository slotRepo;

  /** Auto injected ManagerAppointmentsRepository instance */
  @Autowired private ManagerAppointmentsRepository appointmentsRepository;

  /**
   * Find all manager slots
   *
   * @param managerName Filter by manager name (optional)
   * @return List of found manager slots
   */
  @Override
  public List<ManagerSlotDTO> findAll(Optional<String> managerName) {

    String manager = managerName.isPresent() ? managerName.get() : "%%";

    List<ManagerSlot> slots = slotRepo.findByManagerNameLike(manager);
    return slots.stream().map(slot -> getDtoFromSlot(slot)).collect(Collectors.toList());
  }

  /**
   * Create a new manager slot
   *
   * @param dto DTO carrying manager slot details to be created
   * @return ID of the created manager slot
   */
  @Transactional
  @Override
  public ManagerSlotDTO createManagerSlot(ManagerSlotDTO dto) {
    validateOverlappingWithExistingSlots(
        0, dto.getManagerName(), dto.getStartTime(), dto.getEndTime());
    ManagerSlot slot =
        ManagerSlot.builder()
            .startTime(dto.getStartTime())
            .endTime(dto.getEndTime())
            .managerName(dto.getManagerName())
            .build();
    slotRepo.save(slot);
    return getDtoFromSlot(slot);
  }

  /**
   * Find a manager slot by its row ID
   *
   * @param id ID for which manager slot is to be found
   * @return Found manager slot
   */
  @Override
  public ManagerSlotDTO findById(Long id) {
    ManagerSlot slot = getManagerSlot(id);

    return getDtoFromSlot(slot);
  }

  /**
   * Update manager slot
   *
   * @param id ID of the row that needs to be updated
   * @param dto DTO carrying updated manager slot details
   * @return flag to indicate whether row was updated or not
   */
  @Override
  public ManagerSlotDTO updateManagerSlot(Long id, ManagerSlotDTO dto) {
    ManagerSlot slot = getManagerSlot(id);
    validateOverlappingWithExistingSlots(
        id, dto.getManagerName(), dto.getStartTime(), dto.getEndTime());
    slot.setStartTime(dto.getStartTime());
    slot.setEndTime(dto.getEndTime());
    slotRepo.save(slot);
    return getDtoFromSlot(slot);
  }

  /**
   * Delete a manager slot by its row ID
   *
   * @param id ID for which manager slot is to be deleted
   */
  @Override
  public void deleteManagerSlot(Long id) {
    ManagerSlot slot = getManagerSlot(id);
    slotRepo.delete(slot);
  }

  /**
   * Find available ManagerSlot.
   *
   * @param manager manager id
   * @param day the day of the month
   * @return Found ManagerSlot
   */
  @Override
  public SlotAvailabilityDTO findSlotsAvailability(String manager, String day) {
    List<ManagerSlot> managerSlots = slotRepo.findByManagerNameLike(manager);
    try {
      Date queryDay = new SimpleDateFormat(DB_DATE_FORMAT).parse(day);
      List<SlotAvailabilityDTO.SlotDTO> slotDTOS = Lists.newArrayList();
      for (ManagerSlot slot : managerSlots) {
        List<ManagerAppointments> appointments =
            appointmentsRepository.findBySlotIdAndDay(slot.getId(), queryDay);

        slotDTOS.add(
            SlotAvailabilityDTO.SlotDTO.builder()
                .startTime(slot.getStartTime())
                .endTime(slot.getEndTime())
                .status(
                    appointments.size() > 0
                        ? SlotAvailabilityDTO.UNAVAILABLE
                        : SlotAvailabilityDTO.AVAILABLE)
                .build());
      }
      return SlotAvailabilityDTO.builder().date(day).slots(slotDTOS).build();
    } catch (Exception e) {
      throw new BaseNotFoundException(ErrorCode.RMA_ERR000004.name(), Collections.emptyList());
    }
  }

  /**
   * Find ManagerSlot by Id, and throw exception if not found
   *
   * @param id ID of the row to be found
   * @return Found ManagerSlot
   */
  private ManagerSlot getManagerSlot(Long id) {
    Optional<ManagerSlot> managerSlot = slotRepo.findById(id);

    if (!managerSlot.isPresent()) {
      throw new BaseNotFoundException(ErrorCode.RMA_ERR000004.name(), Collections.emptyList());
    }
    return managerSlot.get();
  }

  /**
   * Validate whether there is an already existing slot with overlapping time with passed time range
   *
   * @param id ID of the slot being compared to. If greater than 0, this record has to be ignored
   *     from lis of found slots
   * @param manager Name of the manager
   * @param startTime Start time
   * @param endTime End time
   */
  private void validateOverlappingWithExistingSlots(
      long id, String manager, int startTime, int endTime) {
    List<ManagerSlot> slots = slotRepo.findSlotsOverlappigWith(manager, startTime, endTime);

    if (id > 0) {
      // collect only records, that are different from current record
      slots = slots.stream().filter(s -> s.getId() != id).collect(Collectors.toList());
    }
    if (slots.size() > 0) {
      log.error("manager slot overlapping with existing slots");
      throw new BaseBadRequestException(ErrorCode.RMA_ERR000003.name(), Collections.emptyList());
    }
  }

  /**
   * Create ManagerSlotDTO instance from ManagerSlot
   *
   * @param slot the model object
   * @return the DTO object
   */
  private ManagerSlotDTO getDtoFromSlot(ManagerSlot slot) {
    return ManagerSlotDTO.builder()
        .id(slot.getId())
        .managerName(slot.getManagerName())
        .startTime(slot.getStartTime())
        .endTime(slot.getEndTime())
        .build();
  }

  /**
   * Get appointment time taking into consideration the requested date and slot start time in
   * seconds
   *
   * @param date the chosen appointment date (may or may not include time). It will also truncate to
   *     start of day time
   * @param slotStartTimeInSecs start time of the slot in seconds
   * @return Appointment start time (date + time)
   */
  private ZonedDateTime getAppointmentDate(ZonedDateTime date, int slotStartTimeInSecs) {
    ZonedDateTime zdt1 = date.truncatedTo(ChronoUnit.DAYS);
    ZonedDateTime actualTime = zdt1.plusSeconds(slotStartTimeInSecs);
    return actualTime;
  }
}
