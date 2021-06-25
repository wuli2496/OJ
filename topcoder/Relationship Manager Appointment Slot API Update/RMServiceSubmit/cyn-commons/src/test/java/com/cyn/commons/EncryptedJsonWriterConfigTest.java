package com.cyn.commons;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EncryptedJsonWriterConfigTest {

  @Test
  void mappingJackson2HttpMessageConverter() {
    assertNotNull(new EncryptedJsonWriterConfig().mappingJackson2HttpMessageConverter());
  }
}
