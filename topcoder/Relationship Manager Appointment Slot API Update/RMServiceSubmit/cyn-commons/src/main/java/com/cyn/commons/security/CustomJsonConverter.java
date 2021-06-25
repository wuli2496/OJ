package com.cyn.commons.security;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.util.StreamUtils;

public class CustomJsonConverter extends MappingJackson2HttpMessageConverter {

  /** A Jackson 2 converter instance */
  private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter =
      new MappingJackson2HttpMessageConverter();

  /**
   * A custom JSON converter for Spring Boot to write encrypted response back to clients without
   * processing as json but keeping the content type as json
   *
   * @param o the actual response object returned from Controller
   * @param type the actual response type returned from Controller
   * @param outputMessage the HTTP output message to be sent by Spring Boot, the encrypted data will
   *     be copied into this message
   * @throws IOException IO exception can occur while writing message to output
   */
  @Override
  protected void writeInternal(Object o, @Nullable Type type, HttpOutputMessage outputMessage)
      throws IOException {
    if (o instanceof String) {
      StreamUtils.copy((String) o, Charset.defaultCharset(), outputMessage.getBody());
    } else {
      mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, outputMessage);
    }
  }
}
