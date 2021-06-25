package com.cyn.commons.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class AsyncMessageAESUtilsTest {

  /**
   * Checks if the encryption and decryption is done correctly using AES
   *
   * @throws Exception - if there is an error while executing the test
   */
  @Test
  void testEncryptionDecryption() throws Exception {
    AsyncMessageAESUtils asyncMessageAESUtils = new AsyncMessageAESUtils();
    String data = "abcd";
    String key = "abcdefghijklmnop";

    ReflectionTestUtils.setField(asyncMessageAESUtils, "asyncMessageEncKey", key);

    String actualResponse = asyncMessageAESUtils.encryptMessage(data);
    assertNotNull(actualResponse);

    actualResponse = asyncMessageAESUtils.decryptMessage(actualResponse, String.class);
    assertEquals(data, actualResponse);
  }
}
