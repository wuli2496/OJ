package com.cyn.commons.config;

import com.cyn.commons.filter.SecurityConfigurator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/** The configuration class for initializing default security configurator. */
@Configuration
public class AppInitializerConfiguration {

  /**
   * The method for creating SecurityConfigurator bean. We use the ConditionalOnMissingBean
   * annotation because if a services overrides the SecurityConfigurator and creates a
   * configuration, there is no need to create a one still
   *
   * @return the SecurityConfigurator bean
   */
  @Bean
  @ConditionalOnMissingBean(SecurityConfigurator.class)
  public SecurityConfigurator securityConfigurator() {
    return new SecurityConfigurator();
  }

  /**
   * The method for creating Rest template bean so that interceptors can be registered
   *
   * @return the RestTemplate bean
   */
  @Bean
  @ConditionalOnMissingBean(RestTemplate.class)
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
