package com.cyn.commons.security;

import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/** Dummy class to be used by PayloadDecryptor test */
@PayloadDecryption
public class TestController2 {
  @PostMapping("/test")
  public void test(@RequestBody final Map<String, String> requestData) {}
}
