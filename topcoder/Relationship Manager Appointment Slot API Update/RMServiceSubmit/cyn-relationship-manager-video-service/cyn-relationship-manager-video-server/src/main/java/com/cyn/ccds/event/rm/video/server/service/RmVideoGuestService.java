package com.cyn.ccds.event.rm.video.server.service;

import com.cyn.ccds.event.rm.video.server.client.GuestDTO;
import com.cyn.ccds.event.rm.video.server.client.SessionDTO;

/** Service interface for guest invitation */
public interface RmVideoGuestService {

  /**
   * Create the new Guest in DB.
   *
   * @param userName logged in user name
   * @param guestDTO that contains sessionId to use in the guest record creation
   * @return guestId and sessionId of the created guest
   * @see GuestDTO
   */
  GuestDTO create(String userName, GuestDTO guestDTO);

  /**
   * Generate token for guest with given id.
   *
   * @param guestId the id of the guest
   * @return token, guestId and sessionId of the requested guest
   * @see GuestDTO
   */
  GuestDTO generateToken(String guestId);

  /**
   * Starts a video session by guest
   *
   * @param guestId the id of the guest.
   * @return Token created by OpenVidu KMS server or else throw errorcode RMV_ERR000006
   */
  SessionDTO startVideoSession(String guestId);

  /**
   * Accept guest.
   *
   * @param userName logged in user name
   * @param guestId the id of the accepted guest
   */
  void accept(String userName, String guestId);

  /**
   * Reject guest.
   *
   * @param userName logged in user name
   * @param guestId the id of the rejected guest
   */
  void reject(String userName, String guestId);
}
