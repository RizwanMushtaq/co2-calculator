package com.rizwanmushtaq.utils;

import static com.rizwanmushtaq.utils.AppConstants.UNKNOWN;

import com.rizwanmushtaq.exceptions.ApplicationPropertiesException;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class ApplicationPropertiesProvider {
  private Configurations configs;

  public ApplicationPropertiesProvider() {
    this.configs = new Configurations();
  }

  public String getVersion() {
    Configuration config;
    try {
      config = configs.properties("application.properties");
    } catch (ConfigurationException e) {
      throw new ApplicationPropertiesException(
          "Failed to load application.properties file: " + e.getMessage());
    }

    String version = config.getString("app.version");
    if (version == null || version.isEmpty()) {
      return UNKNOWN;
    }
    return version;
  }
}
