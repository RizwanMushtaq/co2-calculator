package com.rizwanmushtaq.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.rizwanmushtaq.utils.ExitCodes;
import org.junit.jupiter.api.Test;

class GlobalExceptionHandlerTest {

  @Test
  void handle_userCategory() {
    Exception ex = new InvalidUserInputException("Invalid user input");
    int code = GlobalExceptionHandler.handle(ex, ExceptionCategory.USER, false);
    assertEquals(ExitCodes.INVALID_INPUT, code);
  }

  @Test
  void handle_orsTokenCategory() {
    Exception ex = new ORSTokenException("ORS token error");
    int code = GlobalExceptionHandler.handle(ex, ExceptionCategory.ORS_TOKEN, false);
    assertEquals(ExitCodes.CONFIG_ERROR, code);
  }

  @Test
  void handle_appPropertiesCategory() {
    Exception ex = new ApplicationPropertiesException("App properties loading error");
    int code = GlobalExceptionHandler.handle(ex, ExceptionCategory.APP_PROPERTIES, false);
    assertEquals(ExitCodes.CONFIG_ERROR, code);
  }

  @Test
  void handle_externalCategory() {
    Exception ex = new ExternalAPIException("External API error");
    int code = GlobalExceptionHandler.handle(ex, ExceptionCategory.EXTERNAL, false);
    assertEquals(ExitCodes.EXTERNAL_ERROR, code);
  }

  @Test
  void handle_emissionFactorsCategory() {
    Exception ex = new EmissionFactorsConfigException("Emission factors config error");
    int code = GlobalExceptionHandler.handle(ex, ExceptionCategory.EMISSION_FACTORS, false);
    assertEquals(ExitCodes.CONFIG_ERROR, code);
  }

  @Test
  void handle_unexpectedCategory_withNullPointerException() {
    Exception ex = new NullPointerException("Null pointer exception");
    int code = GlobalExceptionHandler.handle(ex, ExceptionCategory.UNEXPECTED, false);
    assertEquals(ExitCodes.UNEXPECTED_ERROR, code);
  }

  @Test
  void handle_unexpectedCategory_withException() {
    Exception ex = new Exception("General exception");
    int code = GlobalExceptionHandler.handle(ex, ExceptionCategory.UNEXPECTED, false);
    assertEquals(ExitCodes.UNEXPECTED_ERROR, code);
  }

  @Test
  void handle_null() {
    int code = GlobalExceptionHandler.handle(null, ExceptionCategory.UNEXPECTED, false);
    assertEquals(ExitCodes.UNEXPECTED_ERROR, code);
  }
}
