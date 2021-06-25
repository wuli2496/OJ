package com.cyn.ccds.event.rm.video.server.controller;

import com.cyn.ccds.event.rm.video.server.client.GuestDTO;
import com.cyn.ccds.event.rm.video.server.client.SessionDTO;
import com.cyn.ccds.event.rm.video.server.service.RmVideoGuestService;
import com.cyn.commons.api.GenericResponse;
import com.cyn.commons.security.PayloadDecryption;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/** Guest controller. All endpoints accepts encrypted payload. */
@RequestMapping("/guests")
@RestController
public class RmVideoGuestController {

  /** Guest invitation service injected via constructor. */
  @Autowired RmVideoGuestService rmVideoGuestService;

  /**
   * This endpoint is to create the new Guest in DB.
   *
   * <p>Authentication required to access.
   *
   * @param userName logged in user name
   * @param guestDTO that contains sessionId to use in the guest record creation.
   * @return guestId and sessionId of the created guest.
   * @see GuestDTO
   */
  @PayloadDecryption
  @PostMapping
  public GenericResponse<GuestDTO> create(
      @AuthenticationPrincipal String userName, @Valid @RequestBody GuestDTO guestDTO) {
    return new GenericResponse<>(rmVideoGuestService.create(userName, guestDTO));
  }

  /**
   * Generate token for guest with given id.
   *
   * <p>Public access.
   *
   * @param guestId the id of the guest
   * @return token, guestId and sessionId of the requested guest
   * @see GuestDTO
   */
  @PayloadDecryption
  @GetMapping("/{guestId}")
  public GenericResponse<GuestDTO> generateToken(@Valid @PathVariable String guestId) {
    return new GenericResponse<>(rmVideoGuestService.generateToken(guestId));
  }

  /**
   * Starts a video session by guest
   *
   * <p>Public access.
   *
   * @param guestId the id of the guest.
   * @return Return token in GuestDTO.
   */
  @PayloadDecryption
  @GetMapping("/{guestId}/streams")
  public GenericResponse<SessionDTO> startVideoSession(@Valid @PathVariable String guestId) {
    return new GenericResponse<>(rmVideoGuestService.startVideoSession(guestId));
  }

  /**
   * Accept guest.
   *
   * <p>Authentication required to access.
   *
   * @param userName logged in user name
   * @param guestId the id of the accepted guest
   */
  @PayloadDecryption
  @PutMapping("/{guestId}/accept")
  public GenericResponse<?> accept(
      @AuthenticationPrincipal String userName, @Valid @PathVariable String guestId) {
    rmVideoGuestService.accept(userName, guestId);
    return new GenericResponse<>();
  }

  /**
   * Reject guest.
   *
   * <p>Authentication required to access.
   *
   * @param userName logged in user name
   * @param guestId the id of the rejected guest
   */
  @PayloadDecryption
  @PutMapping("/{guestId}/reject")
  public GenericResponse<?> reject(
      @AuthenticationPrincipal String userName, @Valid @PathVariable String guestId) {
    rmVideoGuestService.reject(userName, guestId);
    return new GenericResponse<>();
  }
}
