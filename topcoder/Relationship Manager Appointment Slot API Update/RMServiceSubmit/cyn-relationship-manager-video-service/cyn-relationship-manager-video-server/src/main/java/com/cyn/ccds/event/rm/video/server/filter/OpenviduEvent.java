package com.cyn.ccds.event.rm.video.server.filter;

import lombok.Getter;
import lombok.Setter;

/** Represents Openvidu webhook events from HTTP request body. */
@Getter
@Setter
public class OpenviduEvent {
  /** Event type. */
  private String event;

  /** Session for which the event was triggered. */
  private String sessionId;

  /** Time when the event was triggered. UTC milliseconds. */
  private Long timestamp;

  /** Time when the session started. UTC milliseconds. */
  private Long startTime;

  /** Total duration of the session, in seconds. */
  private Long duration;

  /** Why the session was destroyed. */
  private String reason;

  /**
   * Checks if contains a session destroyed event.
   *
   * @return true if sessionDestroyed is not null.
   */
  public boolean isSessionDestroyed() {
    return "sessionDestroyed".equals(event);
  }
}
