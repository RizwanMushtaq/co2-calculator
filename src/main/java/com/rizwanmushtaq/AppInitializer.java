package com.rizwanmushtaq;

import static com.rizwanmushtaq.config.EmissionFactorsConfig.loadConfig;
import static com.rizwanmushtaq.utils.AppConstants.ORS_TOKEN;
import static com.rizwanmushtaq.utils.AppUtils.checkORSToken;

public class AppInitializer {
  public static void init() {
    checkORSToken(ORS_TOKEN);
    loadConfig();
  }
}
