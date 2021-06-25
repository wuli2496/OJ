package com.cyn.ccds.event.rm.video.server.repository;

import com.cyn.ccds.event.rm.video.server.entity.AppointmentSession;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repository for Sessions Table */
@Repository
public interface SessionRepository extends JpaRepository<AppointmentSession, Long> {

  /**
   * Find session by sessionId.
   *
   * @param sessionId id of the session to find.
   * @return optional of {@link AppointmentSession}
   */
  Optional<AppointmentSession> findBySessionId(String sessionId);

  /**
   * Find session by appointment id.
   *
   * @param appointmentId appointment id
   * @return optional of {@link AppointmentSession}
   */
  Optional<AppointmentSession> findByAppointmentId(long appointmentId);
}
