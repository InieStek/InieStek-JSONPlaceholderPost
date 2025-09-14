package validators;

import exception.ConfigLoadException;
import java.util.Properties;

public class ConfigValidator {

  public static void checkConfigProperties(Properties properties) {
    if (properties == null || properties.isEmpty()) {
      throw new ConfigLoadException("Properties are null or empty.");
    }

    if (!properties.containsKey("api.url")) {
      throw new ConfigLoadException("Missing 'api.url' in config.properties");
    }
    if (!properties.containsKey("output.dir")) {
      throw new ConfigLoadException("Missing 'output.dir' in config.properties");
    }
  }
}
