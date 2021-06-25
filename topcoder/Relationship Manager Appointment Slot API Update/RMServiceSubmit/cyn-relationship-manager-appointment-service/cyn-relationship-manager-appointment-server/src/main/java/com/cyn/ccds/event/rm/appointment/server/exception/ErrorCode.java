package com.cyn.ccds.event.rm.appointment.server.exception;

/** Error codes. */
public enum ErrorCode {

  /** Invalid pagination request * */
  RMA_ERR000001,

  /** Invalid manager slot details * */
  RMA_ERR000002,

  /** Overlapping slot * */
  RMA_ERR000003,

  /** Record not found for ID * */
  RMA_ERR000004,

  /** Not authorised to do actions on other's ratings * */
  RMA_ERR000005,

  /** Requested appointment slots not consecutive */
  RMA_ERR000006,

  /** Appointment time exceeding max limit of 2 hrs * */
  RMA_ERR000007,

  /** Requested slots don't belong to same Manager * */
  RMA_ERR000008,

  /** Not authorised to view other's appointments * */
  RMA_ERR000009,

  /** Requested date and slot clashing with others * */
  RMA_ERR000010,

  /** Appointment attachment couldn't be saved * */
  RMA_ERR000011,

  /** Failed to get session created in video-service for the Appointment * */
  RMA_ERR000012,

  /** Failed to load manager slots on requested day * */
  RMA_ERR000013,

  /** Slot information not present in appointment request * */
  RMA_ERR000014
}
