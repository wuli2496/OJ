package com.cyn.ccds.event.rm.video.server.exception;

/** Error codes. */
public enum ErrorCode {

  /** Failed to get rm appointments. */
  RMV_ERR000001,

  /** Session couldn't be created due to appointment date mismatch. */
  RMV_ERR000002,

  /** Appointment is not yet confirmed. */
  RMV_ERR000003,

  /** Failed to create session */
  RMV_ERR000004,

  /** Failed to retrieve existing session */
  RMV_ERR000005,

  /** User not authorized to start the session */
  RMV_ERR000006,

  /** Video session doesn't exist or yet to start */
  RMV_ERR000007,

  /** Appointment mode should be in video */
  RMV_ERR000008,

  /** Video token doesn't exist */
  RMV_ERR000009,

  /** Failed to retrieve appointment information from appointment service */
  RMV_ERR000010,

  /** Failed to create openvidu connection */
  RMV_ERR000011,

  /** Failed to retrieve existing guest */
  RMV_ERR000012,

  /** Only Relationship Manager may change guest status to Accept/Reject */
  RMV_ERR000013,

  /** Session in the past */
  RMV_ERR000014,

  /** Session couldn't be created due to guest token expired */
  RMV_ERR000015,

  /** Only guests in Accepted status can start session */
  RMV_ERR000016,

  /** Maximum number of guests reached */
  RMV_ERR000017,

  /** Only manager and appointment creator can create new guest */
  RMV_ERR000018,

  /** Invalid sessionId */
  RMV_ERR000019,

  /** Invalid guestId */
  RMV_ERR000020,

  /** Guest token expired */
  RMV_ERR000021,

  /** Empty token */
  RMV_ERR000022,
}
