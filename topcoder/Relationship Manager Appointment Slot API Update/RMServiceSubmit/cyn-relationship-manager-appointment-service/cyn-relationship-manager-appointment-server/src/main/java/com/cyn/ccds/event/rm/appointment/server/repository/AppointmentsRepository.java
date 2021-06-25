package com.cyn.ccds.event.rm.appointment.server.repository;

import com.cyn.ccds.event.rm.appointment.server.entity.Appointments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repository for Appointments table */
public interface AppointmentsRepository extends JpaRepository<Appointments, Long> {

  /**
   * Find appointments for the given userName and status different from passed status
   *
   * @param userName the username
   * @param status the status
   * @param pageable pagination attributes
   * @return Found list of appointments
   */
  Page<Appointments> findByUserNameAndStatusNot(String userName, String status, Pageable pageable);
}
