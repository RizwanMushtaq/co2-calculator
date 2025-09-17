package com.rizwanmushtaq.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class EmissionFactorsConfig {
  private static final Map<String, Double> emissionFactors;

  static {
    try (InputStream is = EmissionFactorsConfig.class
        .getClassLoader()
        .getResourceAsStream("Emission_Factors.json")) {

      if (is == null) {
        throw new IllegalStateException("Emission_Factors.json not found in resources");
      }

      ObjectMapper mapper = new ObjectMapper();
      emissionFactors = mapper.readValue(is, new TypeReference<Map<String, Double>>() {
      });
    } catch (IOException e) {
      throw new RuntimeException("Failed to load transportation.json", e);
    }
  }

  public static double getEmissionFactor(String method) {
    Double value = emissionFactors.get(method);
    if (value == null) {
      throw new IllegalArgumentException("Unknown transportation method: " + method);
    }
    return value / 1000;
  }
}
