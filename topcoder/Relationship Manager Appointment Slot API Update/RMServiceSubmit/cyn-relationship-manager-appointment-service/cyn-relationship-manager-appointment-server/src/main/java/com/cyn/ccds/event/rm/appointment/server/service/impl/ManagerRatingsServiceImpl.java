package com.cyn.ccds.event.rm.appointment.server.service.impl;

import com.cyn.ccds.event.rm.appointment.client.ManagerRatingDTO;
import com.cyn.ccds.event.rm.appointment.client.PaginatedList;
import com.cyn.ccds.event.rm.appointment.server.entity.ManagerRatings;
import com.cyn.ccds.event.rm.appointment.server.exception.ErrorCode;
import com.cyn.ccds.event.rm.appointment.server.repository.ManagerRatingsRepository;
import com.cyn.ccds.event.rm.appointment.server.service.ManagerRatingsService;
import com.cyn.commons.exception.BaseNotFoundException;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/** Implementation class for {@link ManagerRatingsService} */
@Service
public class ManagerRatingsServiceImpl implements ManagerRatingsService {

  /** Auto injected ManagerRatingsRepository instance */
  @Autowired private ManagerRatingsRepository ratingsRepository;

  /**
   * Find all manager ratings for the given manager (optional)
   *
   * @param managerName manager name (optional)
   * @return Found list of manager ratings
   */
  @Override
  public PaginatedList findAll(Optional<String> managerName, int skip, int limit) {
    Sort order = Sort.by(Sort.Order.desc(ManagerRatings.DEFAULT_SORT_COLUMN));
    Pageable pageRequest = PageRequest.of(skip / limit, limit, order);
    Page<ManagerRatings> results = null;
    if (managerName.isPresent() && StringUtils.isNotEmpty(managerName.get())) {
      results = ratingsRepository.findByManagerName(managerName.get(), pageRequest);
    } else {
      results = ratingsRepository.findAll(pageRequest);
    }

    Page<ManagerRatingDTO> pageDtos = results.map(rt -> getRatingsDto(rt));
    return new PaginatedList(pageDtos.getTotalElements(), pageDtos.getContent());
  }

  /**
   * Create a new manager rating
   *
   * @param ratingDTO DTO carrying manager rating details to be created
   * @return ID of the created manager rating
   */
  @Override
  public ManagerRatingDTO createManagerRating(ManagerRatingDTO ratingDTO, String userName) {
    ManagerRatings ratings =
        ManagerRatings.builder()
            .managerName(ratingDTO.getManagerName())
            .rating(ratingDTO.getRating())
            .feedback(ratingDTO.getFeedback())
            .userName(userName)
            .createdDate(ZonedDateTime.now())
            .build();
    ratingsRepository.save(ratings);
    return getRatingsDto(ratings);
  }

  /**
   * Find a manager rating by its row ID
   *
   * @param id ID for which manager rating is to be found
   * @return Found manager rating
   */
  @Override
  public ManagerRatingDTO findById(Long id) {
    ManagerRatings managerRating = getManagerRating(id);
    return getRatingsDto(managerRating);
  }

  /**
   * Update manager rating
   *
   * @param id ID of the row that needs to be updated
   * @param ratingDTO DTO carrying updated manager rating details
   * @param customerUniqueId logged in user name
   * @return flag to indicate whether row was updated or not
   */
  @Override
  public ManagerRatingDTO updateManagerRating(
      Long id, ManagerRatingDTO ratingDTO, String customerUniqueId) {
    ManagerRatings managerRating = getManagerRating(id);
    checkRatingOwnership(managerRating, customerUniqueId);
    managerRating.setRating(ratingDTO.getRating());
    managerRating.setFeedback(ratingDTO.getFeedback());
    ratingsRepository.save(managerRating);
    return getRatingsDto(managerRating);
  }

  /**
   * Delete a manager rating by its row ID
   *
   * @param id ID for which manager slot is to be deleted
   * @param customerUniqueId logged in user name
   */
  @Override
  public void deleteManagerRating(Long id, String customerUniqueId) {
    ManagerRatings managerRating = getManagerRating(id);
    checkRatingOwnership(managerRating, customerUniqueId);
    ratingsRepository.delete(managerRating);
  }

  /**
   * Convert ManagerRatings to ManagerRatingDTO
   *
   * @param rt the db entity
   * @return the DTO
   */
  private ManagerRatingDTO getRatingsDto(ManagerRatings rt) {
    return ManagerRatingDTO.builder()
        .id(rt.getId())
        .managerName(rt.getManagerName())
        .rating(rt.getRating())
        .feedback(rt.getFeedback())
        .createdDate(rt.getCreatedDate())
        .userName(rt.getUserName())
        .build();
  }

  /**
   * Find ManagerRatings by Id, and throw exception if not found
   *
   * @param id ID of the row to be found
   * @return Found ManagerRatings
   */
  private ManagerRatings getManagerRating(Long id) {
    Optional<ManagerRatings> found = ratingsRepository.findById(id);

    if (!found.isPresent()) {
      throw new BaseNotFoundException(ErrorCode.RMA_ERR000004.name(), Collections.emptyList());
    }
    return found.get();
  }

  /**
   * Validate if logged in user has ownership on the manager rating. Throw exception otherwise
   *
   * @param managerRating the manager rating entity
   * @param customerUniqueId the logged in user
   */
  private void checkRatingOwnership(ManagerRatings managerRating, String customerUniqueId) {
    if (!managerRating.getUserName().equalsIgnoreCase(customerUniqueId)) {
      throw new BaseNotFoundException(ErrorCode.RMA_ERR000005.name(), Collections.emptyList());
    }
  }
}
