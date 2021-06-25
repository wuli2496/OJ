package com.cyn.commons.security;

import static com.cyn.commons.exception.ErrorCode.CPT_ERR000001;
import static com.cyn.commons.exception.ErrorCode.CPT_ERR000002;
import static com.cyn.commons.exception.ErrorCode.CPT_ERR000003;
import static com.cyn.commons.exception.ErrorCode.CPT_ERR000004;
import static com.cyn.commons.exception.ErrorCode.CPT_ERR000005;

import com.cyn.commons.exception.BaseServiceException;
import java.util.Collections;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;

/** Internal secret util. */
@Slf4j
public class InternalSecretUtil {

  /** Encryption algorithm. */
  private static final String ALGORITHM = "PBEWithHMACSHA512AndAES_256";

  /** Separator for string representation of identifiers. */
  private static final String SEPARATOR = ":";

  /** Internal secret to be used for encryption. */
  private static String internalSecretKey = System.getenv("AEKM_INTERNAL_SECRET");

  /** Encryptor instance */
  private static StandardPBEStringEncryptor encryptor;

  /**
   * Encrypt data.
   *
   * @param dataToEncrypt The data to encrypt
   * @return encrypted string
   */
  public static String encrypt(String dataToEncrypt) {
    try {
      return getEncryptor().encrypt(dataToEncrypt);
    } catch (Exception e) {
      log.error("Failed to encrypt internal secret", e);
      throw new BaseServiceException(CPT_ERR000001.toString(), Collections.emptyList(), e);
    }
  }

  /**
   * Decrypt data.
   *
   * @param dataToDecrypt The data to decrypt
   * @return decrypted string
   */
  public static String decrypt(String dataToDecrypt) {
    try {
      return getEncryptor().decrypt(dataToDecrypt);
    } catch (Exception e) {
      log.error("Failed to decrypt internal secret", e);
      throw new BaseServiceException(CPT_ERR000002.toString(), Collections.emptyList(), e);
    }
  }

  /**
   * Encrypt AppGuid
   *
   * @param appGuid The AppGuid to encrypt
   * @return encrypted string
   */
  public static String encryptAppGuid(AppGuid appGuid) {
    if (appGuid.getGuid() == null) {
      return encrypt(appGuid.getAppId());
    }
    return encrypt(appGuid.getAppId() + SEPARATOR + appGuid.getGuid());
  }

  /**
   * Decrypt AppGuid
   *
   * @param dataToDecrypt The data to encrypt
   * @return decrypted AppGuid
   */
  public static AppGuid decryptAppGuid(String dataToDecrypt) {
    String decrypted = decrypt(dataToDecrypt);
    String[] arr = decrypted.split(SEPARATOR);
    if (arr.length != 2) {
      throw new BaseServiceException(CPT_ERR000003.toString(), Collections.emptyList());
    }

    UUID guid = null;
    try {
      guid = UUID.fromString(arr[1]);
    } catch (Exception e) {
      log.error(String.format("Invalid guid: %s", arr[1]));
      throw new BaseServiceException(CPT_ERR000004.toString(), Collections.emptyList(), e);
    }
    return new AppGuid(arr[0], guid);
  }

  /**
   * Get encryptor.
   *
   * @return encryptor
   */
  private static synchronized StandardPBEStringEncryptor getEncryptor() {
    if (encryptor != null) {
      return encryptor;
    }
    if (internalSecretKey == null) {
      throw new BaseServiceException(CPT_ERR000005.toString(), Collections.emptyList());
    }
    encryptor = new StandardPBEStringEncryptor();
    encryptor.setPassword(internalSecretKey);
    encryptor.setAlgorithm(ALGORITHM);
    encryptor.setIvGenerator(new RandomIvGenerator());
    return encryptor;
  }

  public static void main(String[] args) {
    internalSecretKey = args[0];
    log.info(
        String.format(
            "Encrypted authorisation:%n %s",
            encryptAppGuid(new AppGuid(args[1], UUID.fromString(args[2])))));
  }
}
