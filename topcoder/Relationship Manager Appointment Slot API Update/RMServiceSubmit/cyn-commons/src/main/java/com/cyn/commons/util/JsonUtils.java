package com.cyn.commons.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/** Utility to contain object mapper */
public class JsonUtils {

  /** Private constructor for utility method to prevent inheritance */
  private JsonUtils() {}

  /**
   * ObjectMapper built by Jackson2ObjectMapperBuilder, it supports Java 8 Optional and Date & Time
   * types.
   */
  public static final ObjectMapper OBJECT_MAPPER =
      Jackson2ObjectMapperBuilder.json()
          .serializationInclusion(JsonInclude.Include.NON_NULL)
          .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
          .build();
}
