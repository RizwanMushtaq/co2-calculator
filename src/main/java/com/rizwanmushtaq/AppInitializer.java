package com.rizwanmushtaq;

import static com.rizwanmushtaq.config.EmissionFactorsConfig.loadConfig;
import static com.rizwanmushtaq.utils.AppUtils.checkORSToken;
import static com.rizwanmushtaq.utils.EnvironmentVariablesProvider.ORS_TOKEN;

public class AppInitializer {
  public static void init() {
    checkORSToken(ORS_TOKEN);
    loadConfig();
  }
}
