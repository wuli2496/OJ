package com.cyn.ccds.event.rm.appointment.server.service;

/** Service interface for StorageService */
public interface StorageService {

  /**
   * Store attachment for the given appointment ID
   *
   * @param appointmentId the appointment ID
   * @param fileName fileName of the attachment
   * @param content content of the attachment
   * @return URL of file stored file
   */
  String storeFile(long appointmentId, String fileName, byte[] content);

  /**
   * Delete attachment for the given appointmentID and file name
   *
   * @param appointmentId the appointment ID
   * @param fileName the fileName
   */
  void deleteFile(long appointmentId, String fileName);
}
