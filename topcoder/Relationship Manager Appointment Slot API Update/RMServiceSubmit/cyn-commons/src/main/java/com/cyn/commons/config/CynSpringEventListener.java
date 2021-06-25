package com.cyn.commons.config;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.stereotype.Component;

/** Application listener for populating volume based secrets. */
@Component
public class CynSpringEventListener
    implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

  /** The root secret path */
  private static final String SECRETS_PATH =
      File.pathSeparator.concat("etc").concat(File.pathSeparator).concat("secrets");

  /** Custom name of the property source */
  private static final String PROPERTY_SOURCE_NAME = "podProps";

  /**
   * This method is triggered when application environment is ready to populate properties
   *
   * @param event ApplicationEnvironmentPreparedEvent instance
   */
  @Override
  public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
    ConfigurableEnvironment environment = event.getEnvironment();
    Properties props = new Properties();
    try (Stream<Path> pathStream = Files.walk(Paths.get(SECRETS_PATH))) {
      pathStream
          .filter(Files::isRegularFile)
          .forEach(
              path -> {
                try {
                  props.put(
                      path.getFileName().toString(),
                      Files.readString(path, StandardCharsets.UTF_8).trim());
                } catch (IOException e) {
                  // Skip the property and let Spring Bootstrap fail if property is not available
                }
              });
      environment
          .getPropertySources()
          .addFirst(new PropertiesPropertySource(PROPERTY_SOURCE_NAME, props));
    } catch (IOException e) {
      // Don't throw error and let Spring fail if property is not available from other sources
    }
  }
}
