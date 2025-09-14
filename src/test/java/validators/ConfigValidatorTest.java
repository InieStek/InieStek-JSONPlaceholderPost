package validators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import exception.ConfigLoadException;
import java.util.Properties;
import org.junit.jupiter.api.Test;

class ConfigValidatorTest {

  @Test
  void checkConfigProperties_shouldNotThrowException_whenPropertiesAreValid() {
    Properties properties = new Properties();
    properties.setProperty("api.url", "http://test.com");
    properties.setProperty("output.dir", "/tmp");
    assertDoesNotThrow(() -> ConfigValidator.checkConfigProperties(properties));
  }

  @Test
  void checkConfigProperties_shouldThrowException_whenApiUrlIsMissing() {
    Properties properties = new Properties();
    properties.setProperty("output.dir", "/tmp");
    ConfigLoadException exception =
        assertThrows(
            ConfigLoadException.class, () -> ConfigValidator.checkConfigProperties(properties));
    assertTrue(exception.getMessage().contains("Missing 'api.url'"));
  }

  @Test
  void checkConfigProperties_shouldThrowException_whenOutputDirIsMissing() {
    Properties properties = new Properties();
    properties.setProperty("api.url", "http://test.com");
    ConfigLoadException exception =
        assertThrows(
            ConfigLoadException.class, () -> ConfigValidator.checkConfigProperties(properties));
    assertTrue(exception.getMessage().contains("Missing 'output.dir'"));
  }

  @Test
  void checkConfigProperties_shouldThrowException_whenPropertiesAreNull() {
    ConfigLoadException exception =
        assertThrows(ConfigLoadException.class, () -> ConfigValidator.checkConfigProperties(null));
    assertTrue(exception.getMessage().contains("Properties are null or empty"));
  }

  @Test
  void checkConfigProperties_shouldThrowException_whenPropertiesAreEmpty() {
    Properties properties = new Properties();
    ConfigLoadException exception =
        assertThrows(
            ConfigLoadException.class, () -> ConfigValidator.checkConfigProperties(properties));
    assertTrue(exception.getMessage().contains("Properties are null or empty"));
  }
}
