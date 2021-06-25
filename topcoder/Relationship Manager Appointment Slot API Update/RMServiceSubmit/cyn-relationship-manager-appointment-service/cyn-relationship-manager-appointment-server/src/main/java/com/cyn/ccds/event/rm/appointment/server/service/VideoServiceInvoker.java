package com.cyn.ccds.event.rm.appointment.server.service;

/** Service interface for invoking rm-video server */
public interface VideoServiceInvoker {

  /**
   * Create a session in rm-video server
   *
   * @param appointmentId the appointmentId
   * @param managerName the name of the manager
   * @param userName the name of the appointment creator
   * @return the created sessionId
   */
  String createSession(Long appointmentId, String managerName, String userName);
}
