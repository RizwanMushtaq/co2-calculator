package com.rizwanmushtaq.exceptions;

import static com.rizwanmushtaq.exceptions.GlobalExceptionHandler.*;
import static com.rizwanmushtaq.utils.AppConstants.DEBUG;

import java.util.Map;
import java.util.function.Function;

public class ExceptionHandlers {
  public static final Map<Class<? extends Exception>, Function<Exception, Integer>> HANDLERS =
      Map.of(
          InvalidTransportationMethodException.class, ex -> userException(ex, DEBUG),
          EmissionFactorsConfigException.class, ex -> emissionFactorsConfigException(ex, DEBUG),
          ExternalAPIException.class, ex -> externalException(ex, DEBUG),
          ORSTokenException.class, ex -> orsTokenException(ex, DEBUG),
          ApplicationPropertiesException.class, ex -> applicationPropertiesException(ex, DEBUG));
}
