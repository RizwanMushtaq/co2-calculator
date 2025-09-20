package com.rizwanmushtaq.exceptions;

import com.rizwanmushtaq.utils.ExitCodes;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor
public class GlobalExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  public static int userException(Exception e, String debug) {
    if (debug == "true") {
      logger.error("User error: ", e);
    } else {
      logger.error("User error: {}", e.getMessage());
    }

    return ExitCodes.INVALID_INPUT;
  }

  public static int orsTokenException(Exception e, String debug) {
    if (debug == "true") {
      logger.error("ORS_TOKEN error: ", e);
    } else {
      logger.error("ORS_TOKEN error: {}", e.getMessage());
    }
    return ExitCodes.CONFIG_ERROR;
  }

  public static int applicationPropertiesException(Exception e, String debug) {
    if (debug == "true") {
      logger.error("Loading Application Properties error: ", e);
    } else {
      logger.error("Loading Application Properties error: {}", e.getMessage());
    }
    return ExitCodes.CONFIG_ERROR;
  }

  public static int externalException(Exception e, String debug) {
    if (debug == "true") {
      logger.error("External/API error: ", e);
    } else {
      logger.error("External/API error: {}", e.getMessage());
    }
    return ExitCodes.EXTERNAL_ERROR;
  }

  public static int emissionFactorsConfigException(Exception e, String debug) {
    if (debug == "true") {
      logger.error("Emission Factors Config error: ", e);
    } else {
      logger.error("Emission Factors Config error: {}", e.getMessage());
    }
    return ExitCodes.CONFIG_ERROR;
  }

  public static int unexpectedException(Exception e, String debug) {
    if (debug == "true") {
      logger.error("Unexpected error: ", e);
    } else {
      logger.error("Unexpected error: {}", e.getMessage());
    }
    return ExitCodes.UNEXPECTED_ERROR;
  }
}
