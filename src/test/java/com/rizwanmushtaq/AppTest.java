package com.rizwanmushtaq;

import static com.rizwanmushtaq.utils.ExitCodes.CONFIG_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

import com.rizwanmushtaq.exceptions.EmissionFactorsConfigException;
import com.rizwanmushtaq.services.EmissionCalculatorService;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import picocli.CommandLine;

class AppTest {

  private EmissionCalculatorService emissionCalculatorService;
  private CommandLine commandLine;

  @Test
  void testCallWithValidArguments() {
    try (MockedStatic<AppInitializer> mockedInit = mockStatic(AppInitializer.class)) {
      appSetup();
      mockedInit.when(AppInitializer::init).thenAnswer(invocation -> null);
      when(emissionCalculatorService.calculateEmissions("Hamburg", "Berlin", "diesel-car-medium"))
          .thenReturn(123.4);

      int exitCode =
          commandLine.execute(
              "--start", "Hamburg",
              "--end", "Berlin",
              "--transportation-method", "diesel-car-medium");

      assertEquals(0, exitCode);
    }
  }

  @Test
  void testCallWithInValidArguments() {
    appSetup();
    int exitCode =
        commandLine.execute(
            "--end", "Berlin",
            "--transportation-method", "diesel-car-medium");

    assertNotEquals(0, exitCode);
  }

  @Test
  void call_shouldReturnHandleExceptionCode_whenInitThrowsException() {
    try (MockedStatic<AppInitializer> mocked = mockStatic(AppInitializer.class)) {
      mocked.when(AppInitializer::init).thenThrow(new EmissionFactorsConfigException("Initialization failed"));

      App app = new App(mock(EmissionCalculatorService.class));
      app.start = "CityA";
      app.end = "CityB";
      app.transportMethod = "diesel-car-small";
      int exitCode = app.call();

      assertEquals(CONFIG_ERROR, exitCode);
    }
  }

  private void appSetup() {
    emissionCalculatorService = mock(EmissionCalculatorService.class);
    App app = new App(emissionCalculatorService);
    commandLine = new CommandLine(app);
  }
}
