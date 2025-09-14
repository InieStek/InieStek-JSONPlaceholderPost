package config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import exception.ConfigLoadException;
import org.junit.jupiter.api.Test;

class AppConfigTest {

  @Test
  void shouldLoadPropertiesWhenFileIsValid() {
    // when
    AppConfig appConfig = new AppConfig("test-config.properties");

    // then
    assertEquals("https://test.com/posts", appConfig.getApiUrl());
    assertEquals("./test-posts", appConfig.getOutputDir());
  }

  @Test
  void shouldThrowExceptionWhenPropertyIsMissing() {
    // when & then
    ConfigLoadException exception = assertThrows(ConfigLoadException.class, () -> new AppConfig("missing-property-config.properties"));
    assertTrue(exception.getMessage().contains("Missing 'output.dir'"));
  }

  @Test
  void shouldThrowExceptionWhenFileDoesNotExist() {
    // when & then
    ConfigLoadException exception = assertThrows(ConfigLoadException.class, () -> new AppConfig("non-existent-file.properties"));
    assertTrue(exception.getMessage().contains("Cannot find non-existent-file.properties"));
  }
}
