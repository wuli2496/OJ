package com.cyn.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class AESUtilsTest {
  /**
   * Checks if the encryption and decryption is done correctly using AES
   *
   * @throws Exception - if there is an error while executing the test
   */
  @Test
  void testEncryptDecryptionWithKey() throws Exception {
    new AESUtils();
    String data = "abcd";
    String key = "abcdefghijklmnop";
    String actualResponse = AESUtils.encryptWithKey(data, key);
    assertNotNull(actualResponse);

    actualResponse = AESUtils.decryptWithKey(actualResponse, key);
    assertEquals(data, actualResponse);
  }

  /**
   * Checks if the encryption and decryption is done correctly using PKC5
   *
   * @throws Exception - if there is an error while executing the test
   */
  @Test
  void testEncryptDecryptionWithPKC5() throws Exception {
    AESUtils aesUtils = new AESUtils();
    String data = "abcd";
    String key = "abcdefghijklmnop";
    byte[] actualResponse = aesUtils.encryptPKCS5(data.getBytes(), key.getBytes());
    assertNotNull(actualResponse);

    actualResponse = aesUtils.decryptPKCS5(actualResponse, key.getBytes());
    assertNotNull(actualResponse);
  }
}
