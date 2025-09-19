package com.rizwanmushtaq.utils;

import com.rizwanmushtaq.exceptions.ApplicationPropertiesException;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import static com.rizwanmushtaq.utils.AppConstants.UNKNOWN;

public class ApplicationPropertiesProvider {
  private final Configuration config;

  public ApplicationPropertiesProvider() {
    try {
      Configurations configs = new Configurations();
      config = configs.properties("application.properties");
    } catch (ConfigurationException e) {
      throw new ApplicationPropertiesException("Failed to load application.properties file: " + e.getMessage());
    }
  }

  public String getVersion() {
    String version = config.getString("app.version");
    if (version == null || version.isEmpty()) {
      return UNKNOWN;
    }
    return version;
  }
}
