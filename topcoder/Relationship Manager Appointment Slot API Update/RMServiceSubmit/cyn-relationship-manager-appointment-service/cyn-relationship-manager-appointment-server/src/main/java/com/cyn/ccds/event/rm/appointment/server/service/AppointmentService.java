package com.cyn.ccds.event.rm.appointment.server.service;

import com.cyn.ccds.event.rm.appointment.client.AppointmentDTO;
import com.cyn.ccds.event.rm.appointment.client.AppointmentRequestDTO;
import com.cyn.ccds.event.rm.appointment.client.PaginatedList;

/** Service interface for Appointment related operations */
public interface AppointmentService {

  /**
   * Find all appointments for the given user
   *
   * @param userName username name
   * @return Found list of user appointments
   */
  PaginatedList findAll(String userName, int skip, int limit);

  /**
   * Create a new appointment
   *
   * @param request DTO appointment details to be created
   * @return ID of the created user appointment
   */
  AppointmentDTO createAppointment(AppointmentRequestDTO request, String userName);

  /**
   * Find an appointment by its row ID
   *
   * @param id ID for which manager rating is to be found
   * @param userName logged in user name
   * @return Found appointment
   */
  AppointmentDTO findById(Long id, String userName);

  /**
   * Update an appointment
   *
   * @param id ID of the row that needs to be updated
   * @param request DTO carrying updated appointment details
   * @return updated appointment
   */
  AppointmentDTO updateAppointment(Long id, AppointmentRequestDTO request, String userName);

  /**
   * Delete an appointment by its row ID
   *
   * @param id ID for which manager slot is to be deleted
   * @param userName logged in user name
   */
  void deleteAppointment(Long id, String userName);
}
