package com.cyn.commons.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AppInitializerConfigurationTest {

  @Test
  void securityConfigurator() {
    assertNotNull(new AppInitializerConfiguration().securityConfigurator());
  }

  @Test
  void restTemplate() {
    assertNotNull(new AppInitializerConfiguration().restTemplate());
  }
}
