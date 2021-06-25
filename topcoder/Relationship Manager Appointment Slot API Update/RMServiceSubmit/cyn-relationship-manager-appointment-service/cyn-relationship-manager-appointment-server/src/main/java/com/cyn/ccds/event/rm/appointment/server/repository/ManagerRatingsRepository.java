package com.cyn.ccds.event.rm.appointment.server.repository;

import com.cyn.ccds.event.rm.appointment.server.entity.ManagerRatings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/** Repository for ManagerRatings table */
public interface ManagerRatingsRepository extends PagingAndSortingRepository<ManagerRatings, Long> {

  /**
   * Get all manager ratings for a manager
   *
   * @param managerName the Manager name
   * @param pageable pagination attributes
   * @return found list of Manager ratings
   */
  Page<ManagerRatings> findByManagerName(String managerName, Pageable pageable);
}
