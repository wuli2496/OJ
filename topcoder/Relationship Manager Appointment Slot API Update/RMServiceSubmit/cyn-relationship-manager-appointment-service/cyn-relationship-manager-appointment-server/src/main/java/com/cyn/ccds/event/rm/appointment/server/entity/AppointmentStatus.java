package com.cyn.ccds.event.rm.appointment.server.entity;

/** Relationship manager appointment status */
public enum AppointmentStatus {

  /** Appointment in pending status */
  PENDING("Pending"),

  /** Appointment in confirmed status */
  CONFIRMED("Confirmed"),

  /** Appointment in deleted status */
  DELETED("Deleted"),

  /** Appointment in rejected status */
  REJECTED("Rejected"),

  /** Appointment in cancelled status */
  CANCELED("Canceled");

  /** Status of the appointment */
  private String status;

  /**
   * Creates a new appointment status.
   *
   * @param status
   */
  AppointmentStatus(String status) {
    this.status = status;
  }

  /**
   * Get status
   *
   * @return
   */
  public String getStatus() {
    return status;
  }
}
