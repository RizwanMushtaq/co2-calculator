package com.rizwanmushtaq;

import static com.rizwanmushtaq.utils.AppConstants.EMISSION_FACTORS_FILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.rizwanmushtaq.config.EmissionFactorsConfig;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class AppInitializerTest {

  @Test
  void init_shouldCallLoadConfigWithCorrectFile() {
    try (MockedStatic<EmissionFactorsConfig> mocked = mockStatic(EmissionFactorsConfig.class)) {
      AppInitializer.init();
      mocked.verify(() -> EmissionFactorsConfig.loadConfig(EMISSION_FACTORS_FILE), times(1));
    }
  }

  @Test
  void init_shouldPropagateException() {
    try (MockedStatic<EmissionFactorsConfig> mocked = mockStatic(EmissionFactorsConfig.class)) {

      mocked
          .when(() -> EmissionFactorsConfig.loadConfig(EMISSION_FACTORS_FILE))
          .thenThrow(new RuntimeException("Failed"));

      RuntimeException ex = assertThrows(RuntimeException.class, AppInitializer::init);
      assertEquals("Failed", ex.getMessage());
    }
  }
}
