package com.cyn.commons.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

class TemplateUtilsTest {

  @Test
  void processTemplateWithTemplate() {
    ClassLoader classLoader = getClass().getClassLoader();
    ClassPathResource resource = new ClassPathResource("template.ftl", classLoader);
    TemplateUtils templateUtils = new TemplateUtils();
    String actual =
        templateUtils.processTemplate(
            resource,
            "t1",
            new HashMap<>() {
              {
                put("name", "topcoder");
              }
            });
    assertEquals("hello", actual);
  }

  @Test
  void processTemplateWithoutTemplate() {
    ClassLoader classLoader = getClass().getClassLoader();
    ClassPathResource resource = new ClassPathResource("template.ftl", classLoader);
    TemplateUtils templateUtils = new TemplateUtils();
    String actual =
        templateUtils.processTemplate(
            resource,
            "Hello world",
            new HashMap<>() {
              {
                put("name", "topcoder");
              }
            });
    assertEquals("Hello world", actual);
  }
}
