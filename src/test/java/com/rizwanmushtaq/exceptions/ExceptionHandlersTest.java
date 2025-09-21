package com.rizwanmushtaq.exceptions;

import static org.junit.jupiter.api.Assertions.*;

import com.rizwanmushtaq.utils.ExitCodes;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExceptionHandlersTest {

  @BeforeEach
  void resetDebugFlag() throws Exception {
    Field field = ExceptionHandlers.class.getDeclaredField("isDebugActive");
    field.setAccessible(true);
    field.set(null, false);
  }

  @Test
  void handlers_shouldContainAllExpectedExceptionClasses() {
    Map<Class<? extends Exception>, Function<Exception, Integer>> handlers =
        ExceptionHandlers.HANDLERS;

    assertTrue(handlers.containsKey(InvalidUserInputException.class));
    assertTrue(handlers.containsKey(EmissionFactorsConfigException.class));
    assertTrue(handlers.containsKey(ExternalAPIException.class));
    assertTrue(handlers.containsKey(ORSTokenException.class));
    assertTrue(handlers.containsKey(ApplicationPropertiesException.class));
    assertEquals(5, handlers.size(), "Expected exactly 5 handlers");
  }

  @Test
  void handler_forInvalidUserInputException_shouldReturnCorrectExitCode() {
    Exception ex = new InvalidUserInputException("Invalid input");
    int exitCode = ExceptionHandlers.HANDLERS.get(InvalidUserInputException.class).apply(ex);

    assertEquals(ExitCodes.INVALID_INPUT, exitCode);
  }

  @Test
  void handler_forORSTokenException_shouldReturnCorrectExitCode() {
    Exception ex = new ORSTokenException("Missing token");
    int exitCode = ExceptionHandlers.HANDLERS.get(ORSTokenException.class).apply(ex);

    assertEquals(ExitCodes.CONFIG_ERROR, exitCode);
  }

  @Test
  void handler_forExternalAPIException_shouldReturnCorrectExitCode() {
    Exception ex = new ExternalAPIException("API failure");
    int exitCode = ExceptionHandlers.HANDLERS.get(ExternalAPIException.class).apply(ex);

    assertEquals(ExitCodes.EXTERNAL_ERROR, exitCode);
  }

  @Test
  void handler_forEmissionFactorsConfigException_shouldReturnCorrectExitCode() {
    Exception ex = new EmissionFactorsConfigException("Config error");
    int exitCode = ExceptionHandlers.HANDLERS.get(EmissionFactorsConfigException.class).apply(ex);

    assertEquals(ExitCodes.CONFIG_ERROR, exitCode);
  }

  @Test
  void handler_forApplicationPropertiesException_shouldReturnCorrectExitCode() {
    Exception ex = new ApplicationPropertiesException("Properties missing");
    int exitCode = ExceptionHandlers.HANDLERS.get(ApplicationPropertiesException.class).apply(ex);

    assertEquals(ExitCodes.CONFIG_ERROR, exitCode);
  }
}
