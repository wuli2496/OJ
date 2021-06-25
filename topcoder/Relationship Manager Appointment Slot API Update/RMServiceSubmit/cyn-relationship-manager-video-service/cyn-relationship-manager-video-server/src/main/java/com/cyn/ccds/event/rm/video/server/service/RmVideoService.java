package com.cyn.ccds.event.rm.video.server.service;

import com.cyn.ccds.event.rm.video.server.client.AppointmentDTO;
import com.cyn.ccds.event.rm.video.server.client.SessionDTO;
import org.springframework.transaction.annotation.Transactional;

/** The RM video service interface. */
public interface RmVideoService {
  /**
   * Creates a new video session for the given appointment.
   *
   * @param appointmentDTO appointmentId against which session created.
   * @return sessionId for starting video session.
   */
  @Transactional
  SessionDTO createSession(String reqUserName, AppointmentDTO appointmentDTO);

  /**
   * Starts a video session for a the given sessionId.
   *
   * @param reqUserName authenticated user
   * @param sessionId Video sessionId used to create OpenVidu session
   * @return Token created by OpenVidu KMS server or else throw errorcode RMV_ERR000006
   */
  SessionDTO startVideoSession(String reqUserName, String sessionId);

  /**
   * Starts a video session for a the given sessionId extends for guests.
   *
   * @param reqUserName authenticated user
   * @param sessionId Video sessionId used to create OpenVidu session
   * @param isGuestUser true if called by guest
   * @return Token created by OpenVidu KMS server or else throw errorcode RMV_ERR000006
   */
  SessionDTO startVideoSession(String reqUserName, String sessionId, boolean isGuestUser);

  /**
   * Leave the session and destroy it.
   *
   * @param sessionDTO Session DTO holding sessionID to be destroyed
   * @return sessionId just destroyed.
   */
  @Transactional
  SessionDTO leaveSession(String reqUserName, SessionDTO sessionDTO);

  /**
   * Get session by appointment id. User can only see their own sessions.
   *
   * @param customerUniqueId user id.
   * @param appointmentId appointment id.
   * @return session dto.
   */
  @Transactional
  SessionDTO getSessionByAppointmentId(String customerUniqueId, long appointmentId);

  /**
   * Handles sessionDestroyed event from OpenVidu. Cleans up in memory maps.
   *
   * @param sessionId id of the session that triggered the event.
   */
  void onDestroySession(String sessionId);
}
