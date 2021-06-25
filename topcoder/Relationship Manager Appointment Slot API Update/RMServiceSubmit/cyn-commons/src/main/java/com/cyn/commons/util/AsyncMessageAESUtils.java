package com.cyn.commons.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** Common gcp message processor */
@Service
public class AsyncMessageAESUtils {

  /** message encryption key */
  @Value("${async.message.encryption.key}")
  String asyncMessageEncKey;

  /** Utility for symmetric encryption */
  private final AESUtils aesUtils = new AESUtils();

  /**
   * decrypt and parse message to gcp message
   *
   * @param messageBody the string body
   * @param tClass the entity class
   * @param <T> the entity
   * @return parsed entity
   */
  public <T> T decryptMessage(String messageBody, Class<T> tClass)
      throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
          IllegalBlockSizeException, BadPaddingException, NoSuchProviderException,
          InvalidKeyException, JsonProcessingException {
    byte[] data =
        this.aesUtils.decryptPKCS5(
            Base64.getDecoder().decode(messageBody.getBytes(StandardCharsets.UTF_8)),
            asyncMessageEncKey.getBytes(StandardCharsets.UTF_8));
    return JsonUtils.OBJECT_MAPPER.readValue(new String(data, StandardCharsets.UTF_8), tClass);
  }

  /**
   * encrypt data using AES algorithm
   *
   * @param obj object to encrypt
   * @return base64 encode encrypted data
   */
  public String encryptMessage(Object obj)
      throws JsonProcessingException, BadPaddingException, InvalidAlgorithmParameterException,
          NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchProviderException,
          NoSuchPaddingException, InvalidKeyException {
    String data = JsonUtils.OBJECT_MAPPER.writeValueAsString(obj);
    return Base64.getEncoder()
        .encodeToString(aesUtils.encryptPKCS5(data.getBytes(), asyncMessageEncKey.getBytes()));
  }
}
