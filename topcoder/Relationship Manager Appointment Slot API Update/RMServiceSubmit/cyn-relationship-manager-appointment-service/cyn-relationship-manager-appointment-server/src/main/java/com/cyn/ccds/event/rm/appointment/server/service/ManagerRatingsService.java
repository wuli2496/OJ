package com.cyn.ccds.event.rm.appointment.server.service;

import com.cyn.ccds.event.rm.appointment.client.ManagerRatingDTO;
import com.cyn.ccds.event.rm.appointment.client.PaginatedList;
import java.util.Optional;

/** Service interface for the manager ratings related operations */
public interface ManagerRatingsService {

  /**
   * Find all manager ratings for the given manager (optional)
   *
   * @param managerName manager name (optional)
   * @return Found list of manager ratings
   */
  PaginatedList findAll(Optional<String> managerName, int skip, int limit);

  /**
   * Create a new manager rating
   *
   * @param ratingDTO DTO carrying manager rating details to be created
   * @return ID of the created manager rating
   */
  ManagerRatingDTO createManagerRating(ManagerRatingDTO ratingDTO, String userName);

  /**
   * Find a manager rating by its row ID
   *
   * @param id ID for which manager rating is to be found
   * @return Found manager rating
   */
  ManagerRatingDTO findById(Long id);

  /**
   * Update manager rating
   *
   * @param id ID of the row that needs to be updated
   * @param ratingDTO DTO carrying updated manager rating details
   * @return flag to indicate whether row was updated or not
   */
  ManagerRatingDTO updateManagerRating(Long id, ManagerRatingDTO ratingDTO, String userName);

  /**
   * Delete a manager rating by its row ID
   *
   * @param id ID for which manager slot is to be deleted
   * @param customerUniqueId logged in user name
   */
  void deleteManagerRating(Long id, String customerUniqueId);
}
