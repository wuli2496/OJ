package com.cyn.ccds.event.rm.appointment.server.entity;

/** Relationship manager appointment modes */
public enum AppointmentMode {

  /** Appointment in video mode */
  VIDEO("Video"),

  /** Appointment in phone mode */
  PHONE("Phone"),

  /** Appointment in branch mode */
  BRANCH("Branch"),

  /** Appointment in home home */
  HOME("Home"),

  /** Appointment in other mode */
  OTHER("Other");

  /** Mode of the appointment */
  private String mode;

  /**
   * Creates a new appointment mode.
   *
   * @param mode
   */
  AppointmentMode(String mode) {
    this.mode = mode;
  }

  /**
   * Get mode
   *
   * @return
   */
  public String getMode() {
    return mode;
  }
}
