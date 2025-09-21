package com.rizwanmushtaq;


import static com.rizwanmushtaq.config.EmissionFactorsConfig.loadConfig;
import static com.rizwanmushtaq.utils.AppConstants.EMISSION_FACTORS_FILE;

public class AppInitializer {
  public static void init() {
    loadConfig(EMISSION_FACTORS_FILE);
  }
}
