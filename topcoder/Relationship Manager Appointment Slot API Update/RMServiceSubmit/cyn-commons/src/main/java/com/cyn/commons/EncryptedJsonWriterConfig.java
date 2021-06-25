package com.cyn.commons;

import com.cyn.commons.security.CustomJsonConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/** Spring Boot Configuration class to override json serialization mechanism */
@Configuration
public class EncryptedJsonWriterConfig {

  /**
   * The custom Jackson2 converter for writing encrypted byte data as json
   *
   * @return jackson converter instance
   */
  @Bean
  @Primary
  public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
    return new CustomJsonConverter();
  }
}
