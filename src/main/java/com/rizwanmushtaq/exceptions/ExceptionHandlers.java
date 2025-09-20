package com.rizwanmushtaq.exceptions;

import static com.rizwanmushtaq.exceptions.GlobalExceptionHandler.*;
import static com.rizwanmushtaq.utils.AppUtils.isDebugSet;

import java.util.Map;
import java.util.function.Function;

public class ExceptionHandlers {
  private static final boolean isDebugActive = isDebugSet();

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
