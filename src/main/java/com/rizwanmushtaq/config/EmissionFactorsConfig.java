package com.rizwanmushtaq.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rizwanmushtaq.exceptions.EmissionFactorsConfigException;
import com.rizwanmushtaq.exceptions.InvalidTransportationMethodException;
import com.rizwanmushtaq.utils.ObjectMapperUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class EmissionFactorsConfig {
  private static Map<String, Double> emissionFactors = null;

  public static double getEmissionFactor(String method) {
    if (emissionFactors == null) {
      throw new EmissionFactorsConfigException(
          "Emission factors not loaded. Call loadConfig() first.");
    }

    Double value = emissionFactors.get(method);
    if (value == null) {
      throw new InvalidTransportationMethodException("Unknown transportation method: " + method);
    }
    return value;
  }

  public static void loadConfig() {
    try (InputStream input =
        EmissionFactorsConfig.class.getClassLoader().getResourceAsStream("Emission_Factors.json")) {

      if (input == null) {
        throw new EmissionFactorsConfigException("Emission_Factors.json not found in resources");
      }

      emissionFactors =
          ObjectMapperUtil.getMapper()
              .readValue(input, new TypeReference<Map<String, Double>>() {});
    } catch (IOException e) {
      throw new EmissionFactorsConfigException(
          "Failed to load Emission_Factors.json" + " - " + e.getMessage());
    }
  }
}
