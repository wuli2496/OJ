package com.cyn.ccds.event.rm.video.server.util;

import java.security.SecureRandom;
import java.util.Base64;

/** Utility to tokens generation */
public class SecureRandomStringGenerator {
  private static final SecureRandom random = new SecureRandom();
  private static final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();

  /**
   * Generate 160bit base64 token for id.
   *
   * @return generated token.
   */
  public static String generate() {
    return generate(20);
  }

  /**
   * Generate base64 token for id.
   *
   * @param sizeInBytes required token length
   * @return generated token.
   */
  public static String generate(int sizeInBytes) {
    byte[] buffer = new byte[sizeInBytes];
    random.nextBytes(buffer);
    return encoder.encodeToString(buffer);
  }
}
