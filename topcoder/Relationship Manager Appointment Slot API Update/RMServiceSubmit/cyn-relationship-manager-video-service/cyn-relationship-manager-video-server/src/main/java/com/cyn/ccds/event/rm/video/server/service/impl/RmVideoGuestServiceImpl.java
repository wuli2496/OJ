package com.cyn.ccds.event.rm.video.server.service.impl;

import static com.cyn.ccds.event.rm.video.server.exception.ErrorCode.RMV_ERR000005;
import static com.cyn.ccds.event.rm.video.server.exception.ErrorCode.RMV_ERR000008;
import static com.cyn.ccds.event.rm.video.server.exception.ErrorCode.RMV_ERR000012;
import static com.cyn.ccds.event.rm.video.server.exception.ErrorCode.RMV_ERR000013;
import static com.cyn.ccds.event.rm.video.server.exception.ErrorCode.RMV_ERR000014;
import static com.cyn.ccds.event.rm.video.server.exception.ErrorCode.RMV_ERR000015;
import static com.cyn.ccds.event.rm.video.server.exception.ErrorCode.RMV_ERR000016;
import static com.cyn.ccds.event.rm.video.server.exception.ErrorCode.RMV_ERR000017;
import static com.cyn.ccds.event.rm.video.server.exception.ErrorCode.RMV_ERR000018;
import static com.cyn.ccds.event.rm.video.server.exception.ErrorCode.RMV_ERR000019;
import static com.cyn.ccds.event.rm.video.server.exception.ErrorCode.RMV_ERR000020;
import static com.cyn.ccds.event.rm.video.server.exception.ErrorCode.RMV_ERR000021;
import static com.cyn.ccds.event.rm.video.server.exception.ErrorCode.RMV_ERR000022;

import com.cyn.ccds.event.rm.appointment.client.AppointmentDTO;
import com.cyn.ccds.event.rm.video.server.client.GuestDTO;
import com.cyn.ccds.event.rm.video.server.client.SessionDTO;
import com.cyn.ccds.event.rm.video.server.entity.AppointmentSession;
import com.cyn.ccds.event.rm.video.server.entity.Guest;
import com.cyn.ccds.event.rm.video.server.entity.GuestStatus;
import com.cyn.ccds.event.rm.video.server.exception.ErrorCode;
import com.cyn.ccds.event.rm.video.server.repository.GuestRepository;
import com.cyn.ccds.event.rm.video.server.repository.SessionRepository;
import com.cyn.ccds.event.rm.video.server.service.AppointmentServiceInvoker;
import com.cyn.ccds.event.rm.video.server.service.RmVideoGuestService;
import com.cyn.ccds.event.rm.video.server.service.RmVideoService;
import com.cyn.ccds.event.rm.video.server.util.SecureRandomStringGenerator;
import com.cyn.commons.exception.BaseBadRequestException;
import com.cyn.commons.exception.BaseNotFoundException;
import com.cyn.commons.exception.BaseServiceException;
import com.cyn.commons.exception.BaseUnauthenticatedException;
import com.cyn.commons.exception.BaseUnauthorizedException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** Guest Invitation Service */
@Slf4j
@RequiredArgsConstructor
@Service
public class RmVideoGuestServiceImpl implements RmVideoGuestService {
  /** Appointment mode in video. */
  private static final String VIDEO_MODE = "video";

  /** Session Repository for interacting with Sessions entity. */
  private final SessionRepository sessionRepository;

  /** Guest Repository for interacting with Guests entity. */
  private final GuestRepository guestRepository;

  /** RM Video Session service invoker. */
  private final RmVideoService rmVideoService;

  /** Appointment service invoker. */
  private final AppointmentServiceInvoker appointmentServiceInvoker;

  /** Collection to track number of guests for the session */
  private Map<String, Integer> sessionGuests = new ConcurrentHashMap<>();

  // Value of the guest token expiration time property in minutes
  @Value("${guest.token.expiration.time}")
  private Integer tokenExpirationTime;

  /**
   * Process the request to create new Guest.
   *
   * <p>Saved record has default status equals to 'Pending'.
   *
   * @param userName logged in user name
   * @param guestDTO that contains sessionId to use in the guest record creation
   * @return guestId and sessionId of the created guest
   * @see GuestDTO
   * @see BaseUnauthorizedException
   */
  @Override
  public GuestDTO create(String userName, GuestDTO guestDTO) {
    // Obtain session
    AppointmentSession session = validateAndGetSession(guestDTO.getSessionId());

    // Guest can be create by the user who created the appointment or the relationship manager
    // associated to that session
    if (!userName.equals(session.getUserName()) && !userName.equals(session.getManagerName())) {
      throw new BaseUnauthorizedException(RMV_ERR000018.name(), Collections.emptyList());
    }

    // Generate UUID for guest
    String guestId = UUID.randomUUID().toString();
    // Create guest with 'Pending' status
    Guest guest =
        Guest.builder()
            .guestId(guestId)
            .session(session)
            .status(GuestStatus.PENDING.getText())
            .build();
    // Save new guests entity to db
    guestRepository.save(guest);

    // Set guest id to return
    guestDTO.setGuestId(guestId);

    return guestDTO;
  }

  /**
   * Generate 160 bit base64 token for guest with given id.
   *
   * @param guestId the id of the guest
   * @return token, guestId and sessionId of the requested guest
   * @see GuestDTO
   */
  @Override
  public GuestDTO generateToken(String guestId) {
    // Obtain guest
    var guest = getGuest(guestId);

    // Create token for guest
    String token = createToken(guest);

    // Obtain sessionId
    String sessionId = guest.getSession().getSessionId();
    // Create and return GuestDTO
    return new GuestDTO(guestId, sessionId, token);
  }

  /**
   * Starts a video session by guest
   *
   * @param guestId the id of the guest.
   * @return Return token in GuestDTO.
   */
  @Override
  public SessionDTO startVideoSession(String guestId) {
    // Obtain guest
    var guest = getGuest(guestId);

    // Check if the guest is eligible to start the session
    validateGuestBeforeSessionStart(guest);

    // Update guest after token use
    useToken(guest);

    // Obtain sessionId
    String sessionId = guest.getSession().getSessionId();

    // Call to start video session by guest
    var videoSession = rmVideoService.startVideoSession(guestId, sessionId, true);

    // Update number of sessions guests
    trackSessionGuest(guest.getSession());

    // return video token
    return videoSession;
  }

  /**
   * Accept guest.
   *
   * @param guestId the id of the accepted guest
   */
  @Override
  public void accept(String userName, String guestId) {
    updateStatus(userName, guestId, GuestStatus.ACCEPTED);
  }

  /**
   * Reject guest.
   *
   * @param guestId the id of the rejected guest
   */
  @Override
  public void reject(String userName, String guestId) {
    updateStatus(userName, guestId, GuestStatus.REJECTED);
  }

  /**
   * Update guests status to Accept/Reject only by Relationship Manager.
   *
   * @param guestId the id of the rejected guest
   * @see BaseUnauthorizedException
   */
  private void updateStatus(String userName, String guestId, GuestStatus newStatus) {
    // Obtain guest
    var guest = getGuest(guestId);

    // Obtain guests session
    var session = guest.getSession();

    // Check that logged in user is the appointment manager
    if (!session.getManagerName().equals(userName)) {
      throw new BaseUnauthorizedException(RMV_ERR000013.name(), Collections.emptyList());
    }

    // Check that token is actual
    checkTokenExpirationTime(guest, RMV_ERR000021);

    // Set status Rejected to guest
    guest.setStatus(newStatus.getText());

    // Update guest status in DB
    guestRepository.save(guest);
  }

  /**
   * Generate token for user, set expiration time, update repository.
   *
   * @param guest entity holding guests data
   * @return token generated token
   * @see BaseServiceException
   */
  private String createToken(Guest guest) {
    // Generate guest token
    String token = SecureRandomStringGenerator.generate();
    // Set token
    guest.setToken(token);
    // Set expiration time
    guest.setExpirationTime(getTokenExpirationTimeForNow());
    // Update guest entity in repository
    guestRepository.save(guest);
    // return token
    return token;
  }

  /**
   * Update guest after token use
   *
   * <ul>
   *   <li>1. Set token to null
   *   <li>2. Set expiration time to null
   *   <li>3. Set status to 'Used'
   *   <li>4. Update guest in repository
   * </ul>
   *
   * @param guest entity holding guests data
   * @see BaseServiceException
   */
  private void useToken(Guest guest) {
    // Set token to null
    guest.setToken(null);
    // set status to 'Used'
    guest.setStatus(GuestStatus.USED.getText());
    // Update guest status in DB
    guestRepository.save(guest);
  }

  /**
   * Increase number of sessions guests by one.
   *
   * @param session guests session
   */
  private void trackSessionGuest(AppointmentSession session) {
    // Obtain current number of guests
    int currentNumberOfGuests = sessionGuests.getOrDefault(session.getSessionId(), 0);

    // Increase number of guests by 1
    sessionGuests.put(session.getSessionId(), currentNumberOfGuests + 1);
  }

  /**
   * Check of the requirements to start session.
   *
   * <ul>
   *   <li>1. Token should not be expired.
   *   <li>2. Status should be 'Accepted'.
   * </ul>
   *
   * @param guest entity holding guest data
   */
  private void validateGuestBeforeSessionStart(Guest guest) {
    // Check token expiration
    checkTokenExpirationTime(guest, RMV_ERR000015);

    // Guest status should be 'Accepted'
    checkGuestStatus(guest, GuestStatus.ACCEPTED, RMV_ERR000016);
  }

  /**
   * If guest token is expired then exception with RMV_ERR000015 message will be thrown.
   *
   * @param guest entity holding token expiration time
   * @param error that will be send to user
   * @see BaseUnauthenticatedException
   */
  private void checkTokenExpirationTime(Guest guest, ErrorCode error) {
    // Check that token was generated
    if (guest.getExpirationTime() == null) {
      throw new BaseUnauthenticatedException(RMV_ERR000022.name(), Collections.emptyList());
    }
    // Obtain current time
    ZonedDateTime now = ZonedDateTime.now();
    // Obtain expired date in current time-zone
    var expiredDate = guest.getExpirationTime().withZoneSameInstant(now.getZone());
    // Throw exception if token expired
    if (expiredDate.isBefore(now)) {
      throw new BaseUnauthenticatedException(error.name(), Collections.emptyList());
    }
  }

  /**
   * If user status is not equals to `expectedStatus` exception with `error` message will be thrown.
   *
   * @param guest to status check
   * @param expectedStatus
   * @param error that will be send to user
   * @see BaseUnauthenticatedException
   */
  private void checkGuestStatus(Guest guest, GuestStatus expectedStatus, ErrorCode error) {
    if (!guest.getStatus().equals(expectedStatus.getText())) {
      throw new BaseUnauthenticatedException(error.name(), Collections.emptyList());
    }
  }

  /**
   * If maximum number of guests reached then exception with RMV_ERR000017 will be thrown.
   *
   * @param session session to check
   * @see BaseUnauthenticatedException
   */
  private void checkNumberOfSessionGuests(AppointmentSession session) {
    // Obtain current number of guests
    int currentNumberOfGuests = sessionGuests.getOrDefault(session.getSessionId(), 0);

    // Compare with maximum number of guests
    if (currentNumberOfGuests >= session.getMaxGuests()) {
      throw new BaseUnauthenticatedException(RMV_ERR000017.name(), Collections.emptyList());
    }
  }

  /**
   * Create token expiration time for current time moment. Use `guest.token.expiration.time`
   * property to generate date in future.
   *
   * @return token expiration time
   */
  private ZonedDateTime getTokenExpirationTimeForNow() {
    return ZonedDateTime.now().plusMinutes(tokenExpirationTime);
  }

  /**
   * Retrieve guest from db and validate it session.
   *
   * @param guestId the id of the requested guest
   * @return guest with valid session
   */
  private Guest getGuest(String guestId) {
    // Check required parameter guestId
    checkId(guestId, RMV_ERR000020);

    // Obtain guest
    Guest guest = findGuestByGuestId(guestId);

    // Validate guest session
    validateSession(guest.getSession());

    // Return found guest with valid session
    return guest;
  }

  /**
   * Retrieve session from db and validate it.
   *
   * @param sessionId the id of the required session
   * @return valid session
   * @see BaseBadRequestException
   */
  private AppointmentSession validateAndGetSession(String sessionId) {
    // Check required parameter sessionId
    checkId(sessionId, RMV_ERR000019);

    // Obtain session
    var session = findSessionBySessionId(sessionId);

    // Check session
    validateSession(session);

    // Return valid session
    return session;
  }

  /**
   * Check that session:
   *
   * <ul>
   *   <li>1. exist,
   *   <li>2. maximum number of guests doesn't reached
   * </ul>
   *
   * <p>Exception with corresponding message will be thrown in case of violation.
   *
   * @param session entity holding session data
   * @see BaseNotFoundException
   */
  private void validateSession(AppointmentSession session) {
    // Check that session exist
    if (session == null) {
      throw new BaseNotFoundException(RMV_ERR000005.name(), Collections.emptyList());
    }

    // Check number of sesssion guests
    checkNumberOfSessionGuests(session);
  }

  /**
   * If session is in the past exception with RMV_ERR000014 message will be thrown.
   *
   * @param session to check appointment date
   * @see BaseServiceException
   */
  private void checkAppointmentDateIsToday(AppointmentDTO appointment) {
    // Obtain the current date
    ZonedDateTime now = ZonedDateTime.now();
    // Convert appointment date into user time-zone
    LocalDate localDate =
        appointment.getAppointmentDate().withZoneSameInstant(now.getZone()).toLocalDate();
    // Check that appointment date is today
    if (!localDate.equals(now.toLocalDate())) {
      throw new BaseServiceException(RMV_ERR000014.name(), Collections.emptyList());
    }
  }

  /**
   * Check that the 'appointment mode' related to session is equals to 'video'.
   *
   * @param session to check appointment date
   * @see BaseServiceException
   */
  private void checkAppointmentModeIsVideo(AppointmentDTO appointment) {
    if (!VIDEO_MODE.equalsIgnoreCase(appointment.getMode())) {
      throw new BaseServiceException(RMV_ERR000008.name(), Collections.emptyList());
    }
  }

  /**
   * Check that id is valid UUID.
   *
   * @param id to validate
   * @param error that will be send to user
   * @see BaseBadRequestException
   */
  private void checkId(String id, ErrorCode error) {
    // Should be not null
    if (id == null) {
      throw new BaseBadRequestException(error.name(), Collections.emptyList());
    }

    // Validate to UUID format
    try {
      UUID uuid = UUID.fromString(id);
    } catch (IllegalArgumentException iae) {
      throw new BaseBadRequestException(error.name(), Collections.emptyList());
    }
  }

  /**
   * Find guest in DB by guestId. Exception BaseNotFoundException will be thrown if guest is not
   * found.
   *
   * @param guestId the id of the required guest
   * @return found guest
   * @see Guest
   * @see BaseNotFoundException
   */
  private Guest findGuestByGuestId(String guestId) {
    // Search guest by guestId in DB
    var foundGuest = guestRepository.findByGuestId(guestId);

    // Throw exception if not found
    if (foundGuest.isEmpty()) {
      throw new BaseNotFoundException(RMV_ERR000012.name(), Collections.emptyList());
    }

    // Return found guest
    return foundGuest.get();
  }

  /**
   * Find session in DB by sessionId. Exception BaseNotFoundException will be thrown if session is
   * not found.
   *
   * @param sessionId the sessionId of the required session
   * @return found session
   * @see AppointmentSession
   * @see BaseNotFoundException
   */
  private AppointmentSession findSessionBySessionId(String sessionId) {
    // Search session by sessionId in DB
    var foundSession = sessionRepository.findBySessionId(sessionId);

    // Throw exception if not found
    if (foundSession.isEmpty()) {
      throw new BaseNotFoundException(RMV_ERR000005.name(), Collections.emptyList());
    }

    // Return found entity
    return foundSession.get();
  }
}
