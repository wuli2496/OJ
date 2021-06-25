package com.cyn.ccds.event.rm.video.server.service;

import com.cyn.ccds.event.rm.appointment.client.AppointmentDTO;

/** Service interface for invoking appointment service. */
public interface AppointmentServiceInvoker {
  /**
   * Invoke the RM appointment service to get appointment info.
   *
   * @param appointmentId id of the appointment to get.
   * @return Appointment details.
   */
  AppointmentDTO getAppointment(long appointmentId);
}
