package com.cyn.commons.security;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/** Dummy class to be used by PayloadDecryptor test */
@Controller
@RequestMapping("/tests")
public class TestController1 {
  @PayloadDecryption
  @PostMapping("/test")
  public void test(@RequestBody final Map<String, String> requestData) {}
}
