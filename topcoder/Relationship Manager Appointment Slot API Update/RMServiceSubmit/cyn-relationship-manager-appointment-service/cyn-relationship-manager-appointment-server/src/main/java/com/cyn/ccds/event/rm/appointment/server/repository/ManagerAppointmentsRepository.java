package com.cyn.ccds.event.rm.appointment.server.repository;

import com.cyn.ccds.event.rm.appointment.server.entity.ManagerAppointments;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/** Repository for ManagerAppointments */
public interface ManagerAppointmentsRepository extends JpaRepository<ManagerAppointments, Long> {

  /**
   * Get manager appointments by appointmentId
   *
   * @param appointmentId the appointment Id
   * @return list of manager appointments found
   */
  List<ManagerAppointments> findByAppointmentsId(Long appointmentId);

  /**
   * Get manager appointments by managerSlotId and appointment date
   *
   * @param managerSlotId the manager slotId
   * @param date the appointment date
   * @return list of manager appointments found
   */
  @Query(
      "SELECT ms FROM ManagerAppointments ms WHERE ms.managerSlotId = :managerSlotId "
          + "AND ms.appointments.appointmentDate=:date")
  List<ManagerAppointments> findWithSameSlotIdAndDate(Long managerSlotId, ZonedDateTime date);

  /**
   * Get manager appointments by managerSlotId and appointment date
   *
   * @param slotId the manager slotId
   * @param queryDate the appointment date
   * @return list of manager appointments found
   */
  @Query(
      "SELECT ms FROM ManagerAppointments ms WHERE ms.managerSlotId = :slotId "
          + "AND DATE(ms.appointments.appointmentDate)=DATE(:queryDate)")
  List<ManagerAppointments> findBySlotIdAndDay(long slotId, Date queryDate);
}
