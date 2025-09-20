package com.rizwanmushtaq.exceptions;

import com.rizwanmushtaq.utils.ExitCodes;
import lombok.Getter;

@Getter
public enum ExceptionCategory {
  USER("User error", ExitCodes.INVALID_INPUT),
  ORS_TOKEN("ORS_TOKEN error", ExitCodes.CONFIG_ERROR),
  APP_PROPERTIES("Loading Application Properties error", ExitCodes.CONFIG_ERROR),
  EXTERNAL("External/API error", ExitCodes.EXTERNAL_ERROR),
  EMISSION_FACTORS("Emission Factors Config error", ExitCodes.CONFIG_ERROR),
  UNEXPECTED("Unexpected error", ExitCodes.UNEXPECTED_ERROR);

  private final String message;
  private final int exitCode;

  ExceptionCategory(String message, int exitCode) {
    this.message = message;
    this.exitCode = exitCode;
  }
}
