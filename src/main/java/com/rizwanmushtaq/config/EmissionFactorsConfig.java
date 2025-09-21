package com.rizwanmushtaq.config;

import static com.rizwanmushtaq.utils.AppConstants.*;
import static com.rizwanmushtaq.utils.ExceptionMessages.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rizwanmushtaq.exceptions.EmissionFactorsConfigException;
import com.rizwanmushtaq.exceptions.InvalidUserInputException;
import com.rizwanmushtaq.utils.ObjectMapperUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class EmissionFactorsConfig {
  private static Map<String, Double> emissionFactors = null;

  public static double getEmissionFactor(String method) {
    if (emissionFactors == null) {
      throw new EmissionFactorsConfigException(EMISSION_FACTORS_NOT_LOADED);
    }

    Double value = emissionFactors.get(method);
    if (value == null) {
      throw new InvalidUserInputException(UNKNOWN_TRANSPORTATION_METHOD + method);
    }
    return value;
  }

  public static void loadConfig(String fileName) {
    try (InputStream input =
        EmissionFactorsConfig.class.getClassLoader().getResourceAsStream(fileName)) {

      if (input == null) {
        throw new EmissionFactorsConfigException(EMISSION_FACTORS_NOT_FOUND);
      }

      emissionFactors =
          ObjectMapperUtil.getMapper()
              .readValue(input, new TypeReference<Map<String, Double>>() {});
    } catch (IOException e) {
      throw new EmissionFactorsConfigException(
          EMISSION_FACTORS_LOADING_FAILED + " - " + e.getMessage());
    }
  }
}
