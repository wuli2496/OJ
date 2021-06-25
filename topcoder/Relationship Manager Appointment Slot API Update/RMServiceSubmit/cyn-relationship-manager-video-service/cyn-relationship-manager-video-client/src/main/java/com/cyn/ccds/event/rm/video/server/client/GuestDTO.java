package com.cyn.ccds.event.rm.video.server.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** RelaDTO for Guest */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestDTO {

  /** UUID of the guest. */
  private String guestId;

  /** UUID of the session for which guest created. */
  private String sessionId;

  /** Guest token that used to handle guest by front-end. */
  private String token;
}
