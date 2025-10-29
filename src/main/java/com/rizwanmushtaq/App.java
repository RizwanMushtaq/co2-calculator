package com.rizwanmushtaq;

import static com.rizwanmushtaq.exceptions.ExceptionHandlers.HANDLERS;
import static com.rizwanmushtaq.exceptions.GlobalExceptionHandler.handle;
import static com.rizwanmushtaq.utils.AppUtils.isDebugSet;
import static com.rizwanmushtaq.utils.AppUtils.printResult;
import static com.rizwanmushtaq.utils.EnvironmentVariablesProvider.CO2_DEBUG;
import static com.rizwanmushtaq.utils.EnvironmentVariablesProvider.ORS_TOKEN;
import static com.rizwanmushtaq.utils.ExceptionMessages.MISSING_ORS_TOKEN;
import static com.rizwanmushtaq.utils.ExitCodes.SUCCESS;

import com.rizwanmushtaq.exceptions.ExceptionCategory;
import com.rizwanmushtaq.exceptions.ORSTokenException;
import com.rizwanmushtaq.services.EmissionCalculatorService;
import com.rizwanmushtaq.services.implementations.OrsEmissionCalculatorService;
import com.rizwanmushtaq.utils.VersionProvider;
import java.util.concurrent.Callable;
import lombok.Getter;
import lombok.Setter;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
    name = "co2-calculator",
    mixinStandardHelpOptions = true,
    versionProvider = VersionProvider.class,
    description = "Calculates CO2 emissions between two cities.")
@Getter
@Setter
public class App implements Callable<Integer> {
  private final EmissionCalculatorService emissionCalculatorService;

  @Option(names = "--start", required = true, description = "Start city")
  private String start;

  @Option(names = "--end", required = true, description = "End city")
  private String end;

  @Option(
      names = "--transportation-method",
      required = true,
      description = "Transportation method (e.g., diesel-car-small, petrol-car-medium etc.)")
  private String transportMethod;

  public App() {
    this(new OrsEmissionCalculatorService());
  }

  public App(EmissionCalculatorService emissionCalculatorService) {
    this.emissionCalculatorService = emissionCalculatorService;
  }

  public static void main(String[] args) {
    try {
      if (ORS_TOKEN == null || ORS_TOKEN.isBlank()) {
        throw new ORSTokenException(MISSING_ORS_TOKEN);
      }
    } catch (ORSTokenException e) {
      int exitCode = handle(e, ExceptionCategory.ORS_TOKEN, isDebugSet(CO2_DEBUG));
      System.exit(exitCode);
    }

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

  private double calculateEmissions(String start, String end, String transportMethod) {
    return emissionCalculatorService.calculateEmissions(start, end, transportMethod);
  }

  private int handleException(Exception e) {
    return HANDLERS
        .getOrDefault(
            e.getClass(), ex -> handle(ex, ExceptionCategory.UNEXPECTED, isDebugSet(CO2_DEBUG)))
        .apply(e);
  }
}
