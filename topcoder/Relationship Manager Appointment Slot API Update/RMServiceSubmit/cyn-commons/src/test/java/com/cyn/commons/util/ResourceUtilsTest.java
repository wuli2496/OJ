package com.cyn.commons.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class ResourceUtilsTest {

  /**
   * Tests whether ResourceUtils is able to read the files from classpath
   *
   * @throws IOException - thrown if there is an error executing the test
   */
  @Test
  void testGetResourceFileAsString() throws IOException {
    String response = ResourceUtils.getResourceFileAsString("test", getClass().getClassLoader());
    assertNull(response);
    response = ResourceUtils.getResourceFileAsString("mock.txt", getClass().getClassLoader());
    assertNotNull(response);
  }
}
