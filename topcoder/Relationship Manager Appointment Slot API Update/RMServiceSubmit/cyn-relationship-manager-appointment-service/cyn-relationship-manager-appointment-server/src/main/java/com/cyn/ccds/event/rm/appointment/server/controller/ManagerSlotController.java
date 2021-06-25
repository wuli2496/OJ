package com.cyn.ccds.event.rm.appointment.server.controller;

import com.cyn.ccds.event.rm.appointment.client.ManagerSlotDTO;
import com.cyn.ccds.event.rm.appointment.client.SlotAvailabilityDTO;
import com.cyn.ccds.event.rm.appointment.server.service.ManagerSlotService;
import com.cyn.commons.api.GenericResponse;
import com.cyn.commons.security.PayloadDecryption;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/** Manager slots controller. */
@RestController
@RequestMapping("/manager-slots")
public class ManagerSlotController {

  /** Auto injected ManagerSlotService instance */
  @Autowired ManagerSlotService slotService;

  /**
   * Creates a manager slot
   *
   * @param slotDTO object carrying slot properties
   * @return created manager slot
   */
  @PayloadDecryption
  @PostMapping
  public GenericResponse<ManagerSlotDTO> createManagerSlot(
      @Valid @RequestBody ManagerSlotDTO slotDTO) {

    return new GenericResponse<>(slotService.createManagerSlot(slotDTO));
  }

  /**
   * Get all manager slots available in DB
   *
   * @param manager optional filter manager name parameter
   * @return list of found manager slots
   */
  @PayloadDecryption
  @GetMapping
  public GenericResponse<List<ManagerSlotDTO>> getAllManagerSlots(
      @RequestParam Optional<String> manager) {

    return new GenericResponse<>(slotService.findAll(manager));
  }

  /**
   * Get manager slot by its ID
   *
   * @param slotId ID of the manager slot
   * @return found manager slot
   */
  @PayloadDecryption
  @GetMapping("/{slotId}")
  public GenericResponse<ManagerSlotDTO> getManagerSlotById(@PathVariable("slotId") long slotId) {

    return new GenericResponse<>(slotService.findById(slotId));
  }

  /**
   * Updates a manager slot
   *
   * @param slotId ID of the manager slot
   * @param slotDTO object carrying updated slot data
   * @return updated manager slot
   */
  @PayloadDecryption
  @PutMapping("/{slotId}")
  public GenericResponse<ManagerSlotDTO> updateManagerSlot(
      @PathVariable("slotId") long slotId, @Valid @RequestBody ManagerSlotDTO slotDTO) {

    return new GenericResponse<>(slotService.updateManagerSlot(slotId, slotDTO));
  }

  /**
   * Delete a manager slot by its ID
   *
   * @param slotId ID of the manager slot to be delted
   */
  @PayloadDecryption
  @DeleteMapping("/{slotId}")
  public GenericResponse deleteManagerSlotById(@PathVariable("slotId") long slotId) {

    slotService.deleteManagerSlot(slotId);
    return new GenericResponse();
  }

  /**
   * Get a manager's slots availability info for a particular day
   *
   * @param managerName manager name
   * @param day date of the day to query the availability (in yyyy-MM-dd format)
   * @return list of found manager slots
   */
  @PayloadDecryption
  @GetMapping("/manager/{managerName}")
  public GenericResponse<SlotAvailabilityDTO> getManagerSlotsAvailability(
      @PathVariable String managerName, @RequestParam String day) {

    return new GenericResponse<>(slotService.findSlotsAvailability(managerName, day));
  }
}
