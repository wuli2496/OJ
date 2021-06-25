package com.cyn.ccds.event.rm.video.server.entity;

/** Guest status */
public enum GuestStatus {

  /** Guest in pending status */
  PENDING("Pending"),

  /** Guest in accepted status */
  ACCEPTED("Accepted"),

  /** Guest in rejected status */
  REJECTED("Rejected"),

  /** Guest in used status */
  USED("Used");

  /** Text name of the guest status */
  private String text;

  /**
   * Creates a new guest status.
   *
   * @param text name of the guest status
   */
  GuestStatus(String text) {
    this.text = text;
  }

  /**
   * Getter for status text.
   *
   * @return status
   */
  public String getText() {
    return text;
  }
}
