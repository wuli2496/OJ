package com.cyn.ccds.event.rm.video.server.service.impl;

import static com.cyn.ccds.event.rm.video.server.exception.ErrorCode.RMV_ERR000002;
import static com.cyn.ccds.event.rm.video.server.exception.ErrorCode.RMV_ERR000003;
import static com.cyn.ccds.event.rm.video.server.exception.ErrorCode.RMV_ERR000004;
import static com.cyn.ccds.event.rm.video.server.exception.ErrorCode.RMV_ERR000005;
import static com.cyn.ccds.event.rm.video.server.exception.ErrorCode.RMV_ERR000006;
import static com.cyn.ccds.event.rm.video.server.exception.ErrorCode.RMV_ERR000007;
import static com.cyn.ccds.event.rm.video.server.exception.ErrorCode.RMV_ERR000008;
import static com.cyn.ccds.event.rm.video.server.exception.ErrorCode.RMV_ERR000009;
import static com.cyn.ccds.event.rm.video.server.exception.ErrorCode.RMV_ERR000011;

import com.cyn.ccds.event.rm.video.server.client.AppointmentDTO;
import com.cyn.ccds.event.rm.video.server.client.SessionDTO;
import com.cyn.ccds.event.rm.video.server.entity.AppointmentSession;
import com.cyn.ccds.event.rm.video.server.repository.SessionRepository;
import com.cyn.ccds.event.rm.video.server.service.AppointmentServiceInvoker;
import com.cyn.ccds.event.rm.video.server.service.RmVideoService;
import com.cyn.commons.exception.BaseNotFoundException;
import com.cyn.commons.exception.BaseServiceException;
import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.ConnectionType;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.SessionProperties;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/** Relationship Manager Video Service */
@Slf4j
@RequiredArgsConstructor
@Service
public class RmVideoServiceImpl implements RmVideoService {
  /** Appointment mode in video. */
  private static final String VIDEO_MODE = "video";

  /** Appointment status: confirmed. */
  private static final String CONFIRMED = "confirmed";

  /** Session Repository for interacting with Sessions entity. */
  private final SessionRepository sessionRepository;

  /** Appointment service invoker. */
  private final AppointmentServiceInvoker appointmentServiceInvoker;

  /** OpenVidu object as entrypoint of the SDK */
  private final OpenVidu openVidu;

  /** Collection to pair session names and OpenVidu Session objects */
  private Map<String, Session> mapSessions = new ConcurrentHashMap<>();

  /**
   * Collection to pair session names and tokens (the inner Map pairs tokens and role associated)
   */
  private Map<String, Map<String, OpenViduRole>> mapSessionNamesTokens = new ConcurrentHashMap<>();

  /**
   * Creates a new video session for the given appointment.
   *
   * @param appointmentDTO appointmentId against which session created.
   * @return sessionId for starting video session.
   */
  @Override
  @Transactional
  public SessionDTO createSession(final String reqUserName, final AppointmentDTO appointmentDTO) {
    var session = sessionRepository.findByAppointmentId(appointmentDTO.getAppointmentId());
    if (session.isPresent()) {
      return new SessionDTO(appointmentDTO.getAppointmentId(), session.get().getSessionId(), "");
    }

    // Check managerName value
    if (appointmentDTO.getManagerName() == null) {
      // Retrieve appointment
      var appointment = appointmentServiceInvoker.getAppointment(appointmentDTO.getAppointmentId());
      // Obtain manager name
      appointmentDTO.setManagerName(appointment.getManagerName());
    }

    var newSession = new AppointmentSession();
    newSession.setUserName(appointmentDTO.getUserName());
    newSession.setManagerName(appointmentDTO.getManagerName());
    newSession.setAppointmentId(appointmentDTO.getAppointmentId());
    newSession.setSessionId(UUID.randomUUID().toString());
    log.debug("Creating session {}", newSession);
    sessionRepository.save(newSession);
    log.debug("Saved into session table ");
    return new SessionDTO(appointmentDTO.getAppointmentId(), newSession.getSessionId(), "");
  }

  /**
   * Starts a video session for a the given sessionId.
   *
   * @param reqUserName authenticated user
   * @param sessionId Video sessionId used to create OpenVidu session
   * @return Token created by OpenVidu KMS server or else throw errorcode RMV_ERR000006
   */
  @Override
  public SessionDTO startVideoSession(final String reqUserName, final String sessionId) {
    return startVideoSession(reqUserName, sessionId, false);
  }

  /**
   * Starts a video session for a the given sessionId extends for guests.
   *
   * @param reqUserName authenticated user
   * @param sessionId Video sessionId used to create OpenVidu session
   * @param isGuestUser true if called by guest
   * @return Token created by OpenVidu KMS server or else throw errorcode RMV_ERR000006
   */
  @Override
  public SessionDTO startVideoSession(
      final String reqUserName, final String sessionId, boolean isGuestUser) {
    var foundSession = sessionRepository.findBySessionId(sessionId);
    if (foundSession.isEmpty()) {
      throw new BaseNotFoundException(RMV_ERR000005.name(), Collections.emptyList());
    }

    if (!isGuestUser) {
      var appointment =
          appointmentServiceInvoker.getAppointment(foundSession.get().getAppointmentId());
      var userName = appointment.getUserName();
      if (!userName.equals(reqUserName)) {
        throw new BaseServiceException(RMV_ERR000006.name(), Collections.emptyList());
      }
      if (!CONFIRMED.equalsIgnoreCase(appointment.getStatus())) {
        throw new BaseServiceException(RMV_ERR000003.name(), Collections.emptyList());
      }
      if (!VIDEO_MODE.equalsIgnoreCase(appointment.getMode())) {
        throw new BaseServiceException(RMV_ERR000008.name(), Collections.emptyList());
      }
      ZonedDateTime now = ZonedDateTime.now();
      LocalDate localDate =
          appointment.getAppointmentDate().withZoneSameInstant(now.getZone()).toLocalDate();
      if (!localDate.equals(now.toLocalDate())) {
        throw new BaseServiceException(RMV_ERR000002.name(), Collections.emptyList());
      }
    }
    return createOpenVidSession(reqUserName, sessionId, foundSession.get().getAppointmentId());
  }

  /**
   * Leave the session and destroy it.
   *
   * @param sessionDTO Session DTO holding sessionID to be destroyed
   * @return sessionId just destroyed.
   */
  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public SessionDTO leaveSession(final String reqUserName, final SessionDTO sessionDTO) {
    final String sessionName = sessionDTO.getSessionId();
    final String token = sessionDTO.getToken();
    if (this.mapSessions.get(sessionName) != null
        && this.mapSessionNamesTokens.get(sessionName) != null) {
      // If the token exists
      if (token != null && this.mapSessionNamesTokens.get(sessionName).remove(token) != null) {
        // User left the session
        if (this.mapSessionNamesTokens.get(sessionName).isEmpty()) {
          // Last user left: session must be removed
          this.mapSessions.remove(sessionName);
        }
      } else {
        // The TOKEN wasn't valid
        throw new BaseServiceException(RMV_ERR000009.name(), Collections.emptyList());
      }
    } else {
      // The SESSION does not exist
      throw new BaseServiceException(RMV_ERR000007.name(), Collections.emptyList());
    }
    return sessionDTO;
  }

  /**
   * Get session by appointment id. User can only see their own sessions.
   *
   * @param customerUniqueId user id.
   * @param appointmentId appointment id.
   * @return session dto.
   */
  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public SessionDTO getSessionByAppointmentId(String customerUniqueId, long appointmentId) {
    var sessionOpt = sessionRepository.findByAppointmentId(appointmentId);
    if (sessionOpt.isEmpty()) {
      throw new BaseNotFoundException(RMV_ERR000005.name(), Collections.emptyList());
    }
    var session = sessionOpt.get();
    if (!session.getUserName().equals(customerUniqueId)) {
      throw new BaseNotFoundException(RMV_ERR000005.name(), Collections.emptyList());
    }
    return new SessionDTO(session.getAppointmentId(), session.getSessionId(), "");
  }

  /**
   * Handles sessionDestroyed event from OpenVidu. Cleans up in memory maps.
   *
   * @param sessionId id of the session that triggered the event.
   */
  @Override
  public void onDestroySession(String sessionId) {
    mapSessions.remove(sessionId);
    mapSessionNamesTokens.remove(sessionId);
  }

  /**
   * Opens a video session in OpenVidu with a token for user to join.
   *
   * @param userName user who started the session.
   * @param sessionId session id
   * @return token to join the session.
   */
  private SessionDTO createOpenVidSession(
      final String userName, final String sessionId, final long appointmentId) {
    // Role associated to this user
    OpenViduRole role = OpenViduRole.PUBLISHER;

    String serverData;
    try {
      // Optional data to be passed to other users when this user connects to the
      // video-call. In this case, a JSON with the value we stored in the HttpSession
      // object on login
      String serverTemplate = "{\"serverData\": \" %s \"}";
      serverData = String.format(serverTemplate, userName);
    } catch (Exception e) {
      log.error("RMV_ERR000004 - server template", e);
      throw new BaseServiceException(RMV_ERR000004.name(), Collections.emptyList(), e);
    }

    // Build connectionProperties object with the serverData and the role
    ConnectionProperties connectionProperties =
        new ConnectionProperties.Builder()
            .type(ConnectionType.WEBRTC)
            .role(role)
            .data(serverData)
            .build();
    String token;
    if (this.mapSessions.get(sessionId) != null) {
      // Session already exists
      log.debug("Using existing session {}", sessionId);
      try {
        // Generate a new token with the recently created connectionProperties
        token = this.mapSessions.get(sessionId).createConnection(connectionProperties).getToken();
        // Update our collection storing the new token
        this.mapSessionNamesTokens.get(sessionId).put(token, role);
      } catch (Exception e) {
        log.error("Failed to create openvidu connection", e);
        mapSessions.remove(sessionId);
        mapSessionNamesTokens.remove(sessionId);
        token = doNewSession(sessionId, connectionProperties);
      }
    } else {
      log.debug("New session {}", sessionId);
      token = doNewSession(sessionId, connectionProperties);
    }
    return new SessionDTO(appointmentId, sessionId, token);
  }

  /**
   * Create a new Openvidu session.
   *
   * @param sessionId session id
   * @param connectionProperties session connection properties
   * @return token to join the session.
   */
  private String doNewSession(String sessionId, ConnectionProperties connectionProperties) {
    // Role associated to this user
    OpenViduRole role = OpenViduRole.PUBLISHER;
    try {
      // Create a new OpenVidu Session
      SessionProperties sessionProperties =
          new SessionProperties.Builder().customSessionId(sessionId).build();
      Session session = this.openVidu.createSession(sessionProperties);
      // Generate a new token with the recently created connectionProperties
      String token = session.createConnection(connectionProperties).getToken();

      // Store the session and the token in our collections
      this.mapSessions.put(sessionId, session);
      this.mapSessionNamesTokens.put(sessionId, new ConcurrentHashMap<>());
      this.mapSessionNamesTokens.get(sessionId).put(token, role);
      return token;
    } catch (Exception e) {
      log.error("Failed to create openvidu connection", e);
      throw new BaseServiceException(RMV_ERR000011.name(), Collections.emptyList(), e);
    }
  }
}
