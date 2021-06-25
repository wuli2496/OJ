package com.cyn.commons.security;

import com.cyn.commons.exception.BaseServiceException;
import com.cyn.commons.exception.ErrorCode;
import com.cyn.commons.util.AESUtils;
import java.util.Base64;
import java.util.Collections;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.springframework.beans.factory.annotation.Value;

/** Converter for encrypting PII * */
@Converter
public class CryptoConverterString implements AttributeConverter<String, String> {

  /** Encryption key * */
  @Value("${data.encryption.key}")
  private String encryptionKey;

  /** Utility for symmetric encryption * */
  private AESUtils aesUtils = new AESUtils();

  /**
   * Encrypt data using AES algorithm
   *
   * @param data data to encrypt
   * @return base64 encode encrypted data
   */
  @Override
  public String convertToDatabaseColumn(String data) {
    try {
      if (data == null) {
        return null;
      }
      return Base64.getEncoder()
          .encodeToString(aesUtils.encryptPKCS5(data.getBytes(), encryptionKey.getBytes()));
    } catch (Exception e) {
      throw new BaseServiceException(ErrorCode.ENC_ERR000002.toString(), Collections.emptyList());
    }
  }

  /**
   * Decrypt a value from db
   *
   * @param dbData data to decrypt, this will be provided by jpa
   * @return the plain data
   */
  @Override
  public String convertToEntityAttribute(String dbData) {
    try {
      if (dbData == null) {
        return null;
      }
      return new String(
          aesUtils.decryptPKCS5(Base64.getDecoder().decode(dbData), encryptionKey.getBytes()));
    } catch (Exception e) {
      throw new BaseServiceException(ErrorCode.DEC_ERR000002.toString(), Collections.emptyList());
    }
  }
}
