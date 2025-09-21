package com.rizwanmushtaq.exceptions;

import static com.rizwanmushtaq.exceptions.GlobalExceptionHandler.*;
import static com.rizwanmushtaq.utils.AppUtils.isDebugSet;
import static com.rizwanmushtaq.utils.EnvironmentVariablesProvider.CO2_DEBUG;

import java.util.Map;
import java.util.function.Function;

public class ExceptionHandlers {
  private static final boolean isDebugActive = isDebugSet(CO2_DEBUG);

  public static final Map<Class<? extends Exception>, Function<Exception, Integer>> HANDLERS =
      Map.of(
          InvalidUserInputException.class, ex -> handle(ex, ExceptionCategory.USER, isDebugActive),
          EmissionFactorsConfigException.class,
              ex -> handle(ex, ExceptionCategory.EMISSION_FACTORS, isDebugActive),
          ExternalAPIException.class, ex -> handle(ex, ExceptionCategory.EXTERNAL, isDebugActive),
          ORSTokenException.class, ex -> handle(ex, ExceptionCategory.ORS_TOKEN, isDebugActive),
          ApplicationPropertiesException.class,
              ex -> handle(ex, ExceptionCategory.APP_PROPERTIES, isDebugActive));
}
