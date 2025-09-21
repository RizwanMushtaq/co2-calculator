package com.rizwanmushtaq.exceptions;

import com.rizwanmushtaq.utils.ExitCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  public static int handle(Exception e, ExceptionCategory category, boolean isDebug) {
    if (e == null) {
      logger.error("{}: Null exception", category.getMessage());
      return ExitCodes.UNEXPECTED_ERROR;
    }

    String message = category.getMessage();
    int exitCode = category.getExitCode();

    if (isDebug) {
      logger.error("{}: ", message, e);
    } else {
      logger.error("{}: {}", message, e.getMessage());
    }

    return exitCode;
  }
}
