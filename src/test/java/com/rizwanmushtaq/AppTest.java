package com.rizwanmushtaq;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.rizwanmushtaq.services.EmissionCalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import picocli.CommandLine;

class AppTest {

  private EmissionCalculatorService emissionCalculatorService;
  private CommandLine commandLine;

  @BeforeEach
  void setup() {
    emissionCalculatorService = mock(EmissionCalculatorService.class);
    App app = new App(emissionCalculatorService);
    commandLine = new CommandLine(app);
  }

  @Test
  void testCallWithValidArguments() {
    try (MockedStatic<AppInitializer> mockedInit = Mockito.mockStatic(AppInitializer.class)) {
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
    int exitCode =
        commandLine.execute(
            "--end", "Berlin",
            "--transportation-method", "diesel-car-medium");

    assertNotEquals(0, exitCode);
  }
}
