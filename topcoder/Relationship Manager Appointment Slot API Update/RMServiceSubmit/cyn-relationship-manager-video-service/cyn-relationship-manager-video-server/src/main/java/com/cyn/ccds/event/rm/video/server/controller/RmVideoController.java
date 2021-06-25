package com.cyn.ccds.event.rm.video.server.controller;

import com.cyn.ccds.event.rm.video.server.client.AppointmentDTO;
import com.cyn.ccds.event.rm.video.server.client.SessionDTO;
import com.cyn.ccds.event.rm.video.server.service.RmVideoService;
import com.cyn.commons.api.GenericResponse;
import com.cyn.commons.security.PayloadDecryption;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/** Relationship Manager Video controller. */
@RestController
public class RmVideoController {

  /** RM video service injected via constructor. */
  @Autowired RmVideoService rmVideoService;

  /**
   * Creates a session based on the provided appointment id.
   *
   * @param appointmentDTO request body with appointment id.
   * @return sessionId of the session
   */
  @PayloadDecryption
  @PostMapping("/sessions")
  public GenericResponse<SessionDTO> createSession(
      @AuthenticationPrincipal String customerUniqueId,
      @Valid @RequestBody AppointmentDTO appointmentDTO) {
    return new GenericResponse<>(rmVideoService.createSession(customerUniqueId, appointmentDTO));
  }

  /**
   * Leave session based on the provided session id.
   *
   * @param sessionDTO sessionId to leave
   * @return the same sessionId.
   */
  @PayloadDecryption
  @PostMapping("/leaveSession")
  public GenericResponse<SessionDTO> leaveSession(
      @AuthenticationPrincipal String customerUniqueId, @Valid @RequestBody SessionDTO sessionDTO) {
    return new GenericResponse<>(rmVideoService.leaveSession(customerUniqueId, sessionDTO));
  }

  /**
   * Starts a video session
   *
   * @param sessionId Session id to stream.
   * @return sessionId of the session to start video on.
   */
  @PayloadDecryption
  @GetMapping("/streams/{sessionId}")
  public GenericResponse<SessionDTO> startVideoSession(
      @AuthenticationPrincipal String customerUniqueId, @Valid @PathVariable String sessionId) {
    return new GenericResponse<>(rmVideoService.startVideoSession(customerUniqueId, sessionId));
  }

  /**
   * Get the video session id by appointment id.
   *
   * @param customerUniqueId user name.
   * @param appointmentId appointment id.
   * @return id of the session.
   */
  @GetMapping("/sessions")
  public GenericResponse<SessionDTO> getSessionByAppointmentId(
      @AuthenticationPrincipal String customerUniqueId, @RequestParam long appointmentId) {
    return new GenericResponse<>(
        rmVideoService.getSessionByAppointmentId(customerUniqueId, appointmentId));
  }
}
