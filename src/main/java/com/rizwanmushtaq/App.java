package com.rizwanmushtaq;

import com.rizwanmushtaq.exceptions.EmissionFactorsConfigException;
import com.rizwanmushtaq.exceptions.ExternalAPIException;
import com.rizwanmushtaq.exceptions.InvalidTransportationMethodException;
import com.rizwanmushtaq.exceptions.ORSTokenException;
import com.rizwanmushtaq.services.EmissionCalculatorService;
import com.rizwanmushtaq.services.implementations.ORSEmissionCalculatorService;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import static com.rizwanmushtaq.exceptions.GlobalExceptionHandler.*;
import static com.rizwanmushtaq.utils.AppConstants.DEBUG;
import static com.rizwanmushtaq.utils.AppUtils.printResult;
import static com.rizwanmushtaq.utils.ExitCodes.SUCCESS;

@Command(
    name = "co2-calculator",
    mixinStandardHelpOptions = true,
    version = "1.0.0",
    description = "Calculates CO2 emissions between two cities."
)
public class App implements Runnable {
  @Option(names = "--start", required = true, description = "Start city")
  private String start;

  @Option(names = "--end", required = true, description = "End city")
  private String end;

  @Option(
      names = "--transportation-method",
      required = true,
      description = "Transportation method (e.g., diesel-car-small, petrol-car-medium etc.)"
  )
  private String transportMethod;

  public static void main(String[] args) {
    int exitCode = new CommandLine(new App()).execute(args);
    System.exit(exitCode == 0 ? SUCCESS : exitCode);
  }

  @Override
  public void run() {
    try {
      AppInitializer.init();
      EmissionCalculatorService emissionCalculatorService =
          new ORSEmissionCalculatorService();
      double result = emissionCalculatorService.calculateEmissions(start, end, transportMethod);

      printResult(result);
    } catch (Exception e) {
      handleException(e);
    }
  }

  private void handleException(Exception e) {
    switch (e) {
      case InvalidTransportationMethodException ex -> userException(ex, DEBUG);
      case EmissionFactorsConfigException ex ->
          emissionFactorsConfigException(ex, DEBUG);
      case ExternalAPIException ex -> externalException(ex, DEBUG);
      case ORSTokenException ex -> orsTokenException(ex, DEBUG);
      default -> unexpectedException(e, DEBUG);
    }
  }
}
