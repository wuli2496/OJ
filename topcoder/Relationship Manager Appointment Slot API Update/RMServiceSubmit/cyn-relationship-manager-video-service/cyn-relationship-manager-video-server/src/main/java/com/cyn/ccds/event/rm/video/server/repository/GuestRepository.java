package com.cyn.ccds.event.rm.video.server.repository;

import com.cyn.ccds.event.rm.video.server.entity.Guest;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repository for Guests Table */
@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

  /**
   * Find guest by guestId.
   *
   * @param guestId id of the guest to find.
   * @return optional of {@link Guest}
   */
  Optional<Guest> findByGuestId(String guestId);
}
