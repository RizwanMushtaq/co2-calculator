package com.rizwanmushtaq;

import static com.rizwanmushtaq.config.EmissionFactorsConfig.loadConfig;

public class AppInitializer {
  public static void init() {
    loadConfig();
  }
}
