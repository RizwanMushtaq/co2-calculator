package com.rizwanmushtaq.exceptions;

import com.rizwanmushtaq.utils.ExitCodes;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor
public class GlobalExceptionHandler {
  private static final Logger logger =
      LoggerFactory.getLogger(GlobalExceptionHandler.class);

  public static void userException(Exception e, boolean debug) {
    if (debug) {
      logger.error("User error: ", e);
    } else {
      logger.error("User error: {}", e.getMessage());
    }

    System.exit(ExitCodes.INVALID_INPUT);
  }

  public static void externalException(Exception e, boolean debug) {
    if (debug) {
      logger.error("External/API error: ", e);
    } else {
      logger.error("External/API error: {}", e.getMessage());
    }
    System.exit(ExitCodes.EXTERNAL_ERROR);
  }

  public static void emissionFactorsConfigException(Exception e, boolean debug) {
    if (debug) {
      logger.error("Emission Factors Config error: ", e);
    } else {
      logger.error("Emission Factors Config error: {}", e.getMessage());
    }
    System.exit(ExitCodes.CONFIG_ERROR);
  }

  public static void unexpectedException(Exception e, boolean debug) {
    if (debug) {
      logger.error("Unexpected error: ", e);
    } else {
      logger.error("Unexpected error: {}", e.getMessage());
    }
    System.exit(ExitCodes.UNEXPECTED_ERROR);
  }
}
