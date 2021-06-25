package com.cyn.ccds.event.rm.appointment.server.repository;

import com.cyn.ccds.event.rm.appointment.server.entity.ManagerSlot;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/** Repository for ManagerSlots table */
public interface ManagerSlotsRepository extends PagingAndSortingRepository<ManagerSlot, Long> {

  /**
   * Get all Manager slots
   *
   * @param managerName manager name to search
   * @return found list of manager slots
   */
  List<ManagerSlot> findByManagerNameLike(String managerName);

  /**
   * Find all overlapping slots for a manager for the given start and end time
   *
   * @param manager the manager name
   * @param startTime start time to check
   * @param endTime end time to check
   * @return found list of slots overlapping with passed start and end time
   */
  @Query(
      "SELECT ms FROM ManagerSlot ms WHERE ms.managerName = :manager "
          + "AND (:startTime BETWEEN ms.startTime AND ms.endTime OR :endTime BETWEEN ms.startTime AND ms.endTime)")
  List<ManagerSlot> findSlotsOverlappigWith(
      @Param("manager") String manager,
      @Param("startTime") int startTime,
      @Param("endTime") int endTime);
}
