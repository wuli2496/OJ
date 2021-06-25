package com.cyn.ccds.event.rm.appointment.server.controller;

import com.cyn.ccds.event.rm.appointment.client.AppointmentDTO;
import com.cyn.ccds.event.rm.appointment.client.AppointmentRequestDTO;
import com.cyn.ccds.event.rm.appointment.client.PaginatedList;
import com.cyn.ccds.event.rm.appointment.server.service.AppointmentService;
import com.cyn.commons.api.GenericResponse;
import com.cyn.commons.security.PayloadDecryption;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/** Appointments controller. */
@RequestMapping("/appointments")
@RestController
public class AppointmentController {

  /** Auto injected AppointmentService instance */
  @Autowired AppointmentService service;

  /**
   * Creates an Appointment
   *
   * @param request object carrying appointment data
   * @return created Appointment
   */
  @PayloadDecryption
  @PostMapping
  public GenericResponse<AppointmentDTO> createAppointment(
      @AuthenticationPrincipal String customerUniqueId,
      @Valid @RequestBody AppointmentRequestDTO request) {
    return new GenericResponse<>(service.createAppointment(request, customerUniqueId));
  }

  /**
   * Get all appointments for the logged-in user
   *
   * @param userName logged in user
   * @param skip number of rows to skip
   * @param limit number of rows to return
   * @return list of found manager ratings
   */
  @PayloadDecryption
  @GetMapping
  public GenericResponse<PaginatedList> getAllAppointments(
      @AuthenticationPrincipal String userName, @RequestParam int skip, @RequestParam int limit) {
    return new GenericResponse<>(service.findAll(userName, skip, limit));
  }

  /**
   * Get appointment by its ID
   *
   * @param userName username of the logged-in user
   * @param appointmentId ID of the appointment
   * @return found appointment
   */
  @PayloadDecryption
  @GetMapping("/{appointmentId}")
  public GenericResponse<AppointmentDTO> getAppointmentById(
      @AuthenticationPrincipal String userName, @PathVariable("appointmentId") long appointmentId) {
    return new GenericResponse<>(service.findById(appointmentId, userName));
  }

  /**
   * Updates an Appointment
   *
   * @param appointmentId ID of the appointment
   * @param request object carrying updated ratings properties
   * @return updated appointment
   */
  @PayloadDecryption
  @PutMapping("/{appointmentId}")
  public GenericResponse<AppointmentDTO> updateManagerRatings(
      @AuthenticationPrincipal String customerUniqueId,
      @PathVariable("appointmentId") long appointmentId,
      @Valid @RequestBody AppointmentRequestDTO request) {
    return new GenericResponse<>(
        service.updateAppointment(appointmentId, request, customerUniqueId));
  }

  /**
   * Delete an Appointment by ID
   *
   * @param userName username of the logged-in user
   * @param appointmentId ID of the appointment to be deleted
   */
  @PayloadDecryption
  @DeleteMapping("/{appointmentId}")
  public GenericResponse deleteManagerRatingsById(
      @AuthenticationPrincipal String userName, @PathVariable("appointmentId") long appointmentId) {
    service.deleteAppointment(appointmentId, userName);
    return new GenericResponse();
  }
}
