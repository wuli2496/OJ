package com.cyn.ccds.event.rm.appointment.server.controller;

import com.cyn.ccds.event.rm.appointment.client.ManagerRatingDTO;
import com.cyn.ccds.event.rm.appointment.client.PaginatedList;
import com.cyn.ccds.event.rm.appointment.server.service.ManagerRatingsService;
import com.cyn.commons.api.GenericResponse;
import com.cyn.commons.security.PayloadDecryption;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/** Manager slots controller. */
@RequestMapping("/manager-ratings")
@RestController
public class ManagerRatingsController {

  /** Auto injected ManagerRatingsService instance */
  @Autowired ManagerRatingsService service;

  /**
   * Creates a manager slot
   *
   * @param ratingDTO object carrying ratings data
   * @return created manager ratings
   */
  @PayloadDecryption
  @PostMapping
  public GenericResponse<ManagerRatingDTO> createManagerRating(
      @AuthenticationPrincipal String customerUniqueId,
      @Valid @RequestBody ManagerRatingDTO ratingDTO) {

    return new GenericResponse<>(service.createManagerRating(ratingDTO, customerUniqueId));
  }

  /**
   * Get all manager ratings available in DB
   *
   * @param userName logged in userName
   * @param manager optional filter manager name parameter
   * @param skip number of rows to skip
   * @param limit number of rows to return
   * @return list of found manager ratings
   */
  @PayloadDecryption
  @GetMapping
  public GenericResponse<PaginatedList> getAllManagerRatings(
      @AuthenticationPrincipal String userName,
      @RequestParam Optional<String> manager,
      @RequestParam int skip,
      @RequestParam int limit) {

    return new GenericResponse<>(service.findAll(manager, skip, limit));
  }

  /**
   * Get manager ratings by its ID
   *
   * @param userName username of the logged-in user
   * @param ratingId ID of the rating
   * @return found manager ratings
   */
  @PayloadDecryption
  @GetMapping("/{ratingId}")
  public GenericResponse<ManagerRatingDTO> getManagerRatingById(
      @AuthenticationPrincipal String userName, @PathVariable("ratingId") long ratingId) {

    return new GenericResponse<>(service.findById(ratingId));
  }

  /**
   * Updates a manager ratings
   *
   * @param userName username of the logged-in user
   * @param ratingId ID of the manager ratings
   * @param ratingDTO object carrying updated ratings data
   * @return updated manager ratings
   */
  @PayloadDecryption
  @PutMapping("/{ratingId}")
  public GenericResponse<ManagerRatingDTO> updateManagerRatings(
      @AuthenticationPrincipal String userName,
      @PathVariable("ratingId") long ratingId,
      @Valid @RequestBody ManagerRatingDTO ratingDTO) {

    return new GenericResponse<>(service.updateManagerRating(ratingId, ratingDTO, userName));
  }

  /**
   * @param userName username of the logged-in user
   * @param ratingId ID of the manager rating to be deleted
   */
  @PayloadDecryption
  @DeleteMapping("/{ratingId}")
  public GenericResponse deleteManagerRatingsById(
      @AuthenticationPrincipal String userName, @PathVariable("ratingId") long ratingId) {

    service.deleteManagerRating(ratingId, userName);
    return new GenericResponse();
  }
}
