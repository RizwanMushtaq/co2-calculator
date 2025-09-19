package com.rizwanmushtaq;

import com.rizwanmushtaq.services.EmissionCalculatorService;
import com.rizwanmushtaq.services.implementations.ORSEmissionCalculatorService;
import com.rizwanmushtaq.utils.VersionProvider;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

import static com.rizwanmushtaq.exceptions.ExceptionHandlers.HANDLERS;
import static com.rizwanmushtaq.exceptions.GlobalExceptionHandler.unexpectedException;
import static com.rizwanmushtaq.utils.AppConstants.DEBUG;
import static com.rizwanmushtaq.utils.AppUtils.printResult;
import static com.rizwanmushtaq.utils.ExitCodes.SUCCESS;

@Command(
    name = "co2-calculator",
    mixinStandardHelpOptions = true,
    versionProvider = VersionProvider.class,
    description = "Calculates CO2 emissions between two cities."
)
public class App implements Callable<Integer> {
  private final EmissionCalculatorService emissionCalculatorService;

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

  public App() {
    this(new ORSEmissionCalculatorService());
  }

  public App(EmissionCalculatorService emissionCalculatorService) {
    this.emissionCalculatorService = emissionCalculatorService;
  }

  public static void main(String[] args) {
    int exitCode = new CommandLine(new App()).execute(args);
    System.exit(exitCode);
  }

  @Override
  public Integer call() {
    try {
      AppInitializer.init();
      double result = calculateEmissions(start, end, transportMethod);
      printResult(result);
      return SUCCESS;
    } catch (Exception e) {
      return handleException(e);
    }
  }

  private double calculateEmissions(String start, String end,
                                    String transportMethod) {
    return emissionCalculatorService.calculateEmissions(start, end, transportMethod);
  }

  private int handleException(Exception e) {
    return HANDLERS.getOrDefault(e.getClass(), ex -> unexpectedException(ex, DEBUG)).apply(e);
  }
}
