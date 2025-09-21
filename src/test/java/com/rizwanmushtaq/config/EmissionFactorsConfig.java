package com.rizwanmushtaq.config;

import static com.rizwanmushtaq.utils.AppConstants.EMISSION_FACTORS_FILE;
import static com.rizwanmushtaq.utils.ExceptionMessages.*;
import static org.junit.jupiter.api.Assertions.*;

import com.rizwanmushtaq.exceptions.EmissionFactorsConfigException;
import java.lang.reflect.Field;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class EmissionFactorsConfigTest {

  @AfterEach
  void cleanUpAfterTest() throws Exception {
    clearEmissionFactors();
  }

  @Test
  void getEmissionFactor_beforeLoad_shouldThrowException() {
    EmissionFactorsConfigException ex =
        assertThrows(
            EmissionFactorsConfigException.class,
            () -> EmissionFactorsConfig.getEmissionFactor("diesel-car-small"));
    assertTrue(ex.getMessage().contains("Emission factors not loaded"));
  }

  @Test
  void getEmissionFactor_unknownMethod_shouldThrowException() throws Exception {
    EmissionFactorsConfigException ex =
        assertThrows(
            EmissionFactorsConfigException.class,
            () -> EmissionFactorsConfig.getEmissionFactor("unknown-method"));

    assertTrue(ex.getMessage().contains(EMISSION_FACTORS_NOT_LOADED));
  }

  @Test
  void getEmissionFactor_knownMethod_shouldReturnValue() throws Exception {
    setEmissionFactors(Map.of("diesel-car-small", 0.21));
    double value = EmissionFactorsConfig.getEmissionFactor("diesel-car-small");
    assertEquals(0.21, value);
  }

  @Test
  void loadConfigSuccess() throws Exception {
    assertDoesNotThrow(() -> EmissionFactorsConfig.loadConfig(EMISSION_FACTORS_FILE));

    double dieselCarSmall = EmissionFactorsConfig.getEmissionFactor("diesel-car-small");
    double petrolCarMedium = EmissionFactorsConfig.getEmissionFactor("petrol-car-medium");
    assertEquals(142, dieselCarSmall);
    assertEquals(192, petrolCarMedium);
  }

  @Test
  void loadConfigFailed() throws Exception {
    EmissionFactorsConfigException ex =
        assertThrows(
            EmissionFactorsConfigException.class,
            () -> EmissionFactorsConfig.loadConfig("unknown-method"));

    assertTrue(ex.getMessage().contains(EMISSION_FACTORS_NOT_FOUND));
  }

  private void setEmissionFactors(Map<String, Double> map) throws Exception {
    Field field = EmissionFactorsConfig.class.getDeclaredField("emissionFactors");
    field.setAccessible(true);
    field.set(null, map);
  }

  private void clearEmissionFactors() throws Exception {
    Field field = EmissionFactorsConfig.class.getDeclaredField("emissionFactors");
    field.setAccessible(true);
    field.set(null, null);
  }
}
