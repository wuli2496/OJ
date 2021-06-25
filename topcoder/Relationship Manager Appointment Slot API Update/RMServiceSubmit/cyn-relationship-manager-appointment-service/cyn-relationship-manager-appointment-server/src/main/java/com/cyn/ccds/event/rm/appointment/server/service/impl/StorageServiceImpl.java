package com.cyn.ccds.event.rm.appointment.server.service.impl;

import com.cyn.ccds.event.rm.appointment.server.service.StorageService;
import com.google.cloud.storage.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** Implementation class for {@link StorageService} */
@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

  /** File name format used for GCP storage */
  private static final String BLOB_NAME_FORMAT = "APT_%d_%s";

  /** File URL format valid for GCP storage */
  private static final String BLOB_URL_FORMAT = "https://storage.cloud.google.com/%s/%s";

  /** Auto injected GCP store projectId */
  @Value("${gcp.store.projectId}")
  private String projectId;

  /** Auto injected GCP store bucket name */
  @Value("${gcp.store.bucket}")
  private String bucketName;

  /**
   * Store attachment for the given appointment ID
   *
   * @param appointmentId the appointment ID
   * @param fileName fileName of the attachment
   * @param content content of the attachment
   * @return URL of file stored file
   */
  @Override
  public String storeFile(long appointmentId, String fileName, byte[] content) {
    Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
    BlobId blobId = BlobId.of(bucketName, getBlobName(appointmentId, fileName));
    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
    storage.create(blobInfo, content);
    log.info("File " + fileName + " uploaded to bucket " + bucketName + " as ");
    return getBlobAuthenticatedUrl(getBlobName(appointmentId, fileName));
  }

  /**
   * Delete attachment for the given appointmentID and file name
   *
   * @param appointmentId the appointment ID
   * @param fileName the fileName
   */
  @Override
  public void deleteFile(long appointmentId, String fileName) {
    Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
    storage.delete(bucketName, getBlobName(appointmentId, fileName));
  }

  /**
   * get URL for GCP store uploaded file
   *
   * @param fileName fileName
   * @return uploaded file URL
   */
  private String getBlobAuthenticatedUrl(String fileName) {
    return String.format(BLOB_URL_FORMAT, bucketName, fileName);
  }

  /**
   * get blob name for GCP store uploaded file
   *
   * @param appointmentId the appointment ID
   * @param fileName fileName
   * @return uploaded file name
   */
  private String getBlobName(long appointmentId, String fileName) {
    return String.format(BLOB_NAME_FORMAT, appointmentId, fileName);
  }
}
