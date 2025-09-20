package com.rizwanmushtaq.exceptions;

import static com.rizwanmushtaq.exceptions.GlobalExceptionHandler.*;
import static com.rizwanmushtaq.utils.EnvironmentVariablesProvider.CO2_DEBUG;

import java.util.Map;
import java.util.function.Function;

public class ExceptionHandlers {
  public static final Map<Class<? extends Exception>, Function<Exception, Integer>> HANDLERS =
      Map.of(
          InvalidUserInputException.class, ex -> userException(ex, CO2_DEBUG),
          EmissionFactorsConfigException.class, ex -> emissionFactorsConfigException(ex, CO2_DEBUG),
          ExternalAPIException.class, ex -> externalException(ex, CO2_DEBUG),
          ORSTokenException.class, ex -> orsTokenException(ex, CO2_DEBUG),
          ApplicationPropertiesException.class,
              ex -> applicationPropertiesException(ex, CO2_DEBUG));
}
