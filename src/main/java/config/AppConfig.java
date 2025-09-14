package config;

import static validators.ConfigValidator.checkConfigProperties;

import exception.ConfigLoadException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
  private final Properties properties;

  public AppConfig() {
    this("config.properties");
  }

  public AppConfig(String resourceName) {
    this.properties = new Properties();
    try (InputStream input = getClass().getClassLoader().getResourceAsStream(resourceName)) {
      if (input == null) {
        throw new ConfigLoadException("Cannot find " + resourceName + " on the classpath");
      }
      properties.load(input);
      checkConfigProperties(properties);
    } catch (IOException ex) {
      throw new ConfigLoadException("Error loading " + resourceName, ex);
    }
  }

  public String getApiUrl() {
    return properties.getProperty("api.url");
  }

  public String getOutputDir() {
    return properties.getProperty("output.dir");
  }
}
