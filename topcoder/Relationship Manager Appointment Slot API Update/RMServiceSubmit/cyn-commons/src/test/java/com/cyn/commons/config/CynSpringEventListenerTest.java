package com.cyn.commons.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.mock.env.MockEnvironment;

@ExtendWith(MockitoExtension.class)
class CynSpringEventListenerTest {

  @Mock private ApplicationEnvironmentPreparedEvent applicationEnvironmentPreparedEvent;

  @Mock private MutablePropertySources mutablePropertySources;

  @Test
  void onApplicationEvent() throws IOException {
    File secretsDirectory =
        new File(File.pathSeparator.concat("etc").concat(File.pathSeparator).concat("secrets"));

    secretsDirectory.mkdir();
    String secretPath = secretsDirectory.getAbsolutePath() + "/secret1";
    File secret = new File(secretPath);
    secret.createNewFile();

    CynSpringEventListener springEventListener = new CynSpringEventListener();
    MockEnvironment mockEnvironment = new MockEnvironment();
    doReturn(mockEnvironment).when(applicationEnvironmentPreparedEvent).getEnvironment();
    springEventListener.onApplicationEvent(applicationEnvironmentPreparedEvent);
    assertNotNull(mockEnvironment.getPropertySources().get("podProps"));
  }

  @AfterEach
  void tearDown() {
    File secretsDirectory =
        new File(File.pathSeparator.concat("etc").concat(File.pathSeparator).concat("secrets"));

    if (secretsDirectory.exists()) {
      String[] entries = secretsDirectory.list();
      for (String s : entries) {
        File currentFile = new File(secretsDirectory.getPath(), s);
        currentFile.delete();
      }
      secretsDirectory.delete();
    }
  }
}
