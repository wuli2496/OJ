package com.cyn.ccds.event.rm.appointment.server.service;

import com.cyn.ccds.event.rm.appointment.client.ManagerSlotDTO;
import com.cyn.ccds.event.rm.appointment.client.SlotAvailabilityDTO;
import java.util.List;
import java.util.Optional;

/** Service interface for the manager slot related operations */
public interface ManagerSlotService {

  /**
   * Find all manager slots for the given manager (optional)
   *
   * @param managerName manager name (optional)
   * @return Found manager slots wrapped in PaginatedList
   */
  List<ManagerSlotDTO> findAll(Optional<String> managerName);

  /**
   * Create a new manager slot
   *
   * @param slotDTO DTO carrying manager slot details to be created
   * @return ID of the created manager slot
   */
  ManagerSlotDTO createManagerSlot(ManagerSlotDTO slotDTO);

  /**
   * Find a manager slot by its row ID
   *
   * @param id ID for which manager slot is to be found
   * @return Found manager slot
   */
  ManagerSlotDTO findById(Long id);

  /**
   * Update manager slot
   *
   * @param id ID of the row that needs to be updated
   * @param slotDTO DTO carrying updated manager slot details
   * @return flag to indicate whether row was updated or not
   */
  ManagerSlotDTO updateManagerSlot(Long id, ManagerSlotDTO slotDTO);

  /**
   * Delete a manager slot by its row ID
   *
   * @param id ID for which manager slot is to be deleted
   */
  void deleteManagerSlot(Long id);

  SlotAvailabilityDTO findSlotsAvailability(String manager, String day);
}
