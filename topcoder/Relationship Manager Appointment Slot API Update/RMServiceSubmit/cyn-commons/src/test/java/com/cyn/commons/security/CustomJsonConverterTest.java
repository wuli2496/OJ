package com.cyn.commons.security;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.springframework.mock.http.MockHttpOutputMessage;

class CustomJsonConverterTest {

  @Test
  void writeInternalForString() throws IOException {
    CustomJsonConverter customJsonConverter = new CustomJsonConverter();
    MockHttpOutputMessage message = new MockHttpOutputMessage();
    customJsonConverter.writeInternal("hello world", null, message);
    assertEquals("hello world", message.getBodyAsString());
  }

  @Test
  void writeInternalForJSON() throws IOException {
    CustomJsonConverter customJsonConverter = new CustomJsonConverter();
    MockHttpOutputMessage message = new MockHttpOutputMessage();
    customJsonConverter.writeInternal(
        new HashMap<>() {
          {
            put("key", "value");
          }
        },
        null,
        message);
    assertEquals("{\"key\":\"value\"}", message.getBodyAsString());
  }
}
