package com.cyn.ccds.event.rm.appointment.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Relationship Manager video session DTO */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTO {

  /** Appointment against which sessions created. */
  private String sessionId;

  /** Video session token. */
  private String token;
}
