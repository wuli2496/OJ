package com.cyn.commons.util;

import static org.junit.jupiter.api.Assertions.*;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import org.junit.jupiter.api.Test;

class RSAUtilsTest {

  RSAUtils rsaUtils;

  public RSAUtilsTest() throws Exception {
    this.rsaUtils = new RSAUtils();
  }

  /**
   * Tests if encryption and decryption can be performed correctly via RSA
   *
   * @throws GeneralSecurityException - thrown if there is an issue during encryption
   */
  @Test
  void testFunctionalities() throws GeneralSecurityException {
    KeyPair keyPair = rsaUtils.generateKeyPair();
    assertNotNull(keyPair);

    String input = "testString";

    byte[] encryptedBytes = rsaUtils.encrypt(input.getBytes(), keyPair.getPublic());
    assertNotNull(encryptedBytes);
    assertTrue(encryptedBytes.length > 0);

    byte[] decryptedBytes = rsaUtils.decrypt(encryptedBytes, keyPair.getPrivate());
    assertEquals(input, new String(decryptedBytes));
  }
}
