package com.cyn.ccds.event.rm.video.server;

import io.openvidu.java.client.OpenVidu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/** The main spring boot entry point */
@SpringBootApplication(
    scanBasePackages = {
      "com.cyn.ccds.event.rm.video.server",
      "com.cyn.commons",
      "com.cyn.ccds.authentication.filter"
    })
@Slf4j
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  /**
   * Creates the OpenVidu bean.
   *
   * @param secret openvidu secret.
   * @param openviduUrl openvidu url.
   * @return an instance of {@link OpenVidu} class.
   */
  @Bean
  public OpenVidu getOpenVidu(
      @Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl) {
    log.debug(
        "Openvidu instance: {} - {}",
        openviduUrl,
        secret.substring(0, 5) + "---" + secret.substring(secret.length() - 5, secret.length()));
    return new OpenVidu(openviduUrl, secret);
  }
}
