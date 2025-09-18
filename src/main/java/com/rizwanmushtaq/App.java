package com.rizwanmushtaq;

import com.rizwanmushtaq.exceptions.*;
import com.rizwanmushtaq.services.implementations.ORSDistanceService;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import static com.rizwanmushtaq.config.EmissionFactorsConfig.getEmissionFactor;
import static com.rizwanmushtaq.config.EmissionFactorsConfig.loadConfig;
import static com.rizwanmushtaq.exceptions.GlobalExceptionHandler.*;
import static com.rizwanmushtaq.utils.AppConstants.DEBUG;
import static com.rizwanmushtaq.utils.AppConstants.ORS_TOKEN;
import static com.rizwanmushtaq.utils.AppUtils.checkORSToken;
import static com.rizwanmushtaq.utils.AppUtils.printResult;
import static com.rizwanmushtaq.utils.ExitCodes.SUCCESS;
import static com.rizwanmushtaq.utils.NumberUtils.truncate;

@Command(
    name = "co2-calculator",
    mixinStandardHelpOptions = true,
    version = "1.0.0",
    description = "Calculates CO2 emissions between two cities."
)
public class App implements Runnable {
  private static final ORSDistanceService distanceService = new ORSDistanceService();

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
      checkORSToken(ORS_TOKEN);
      loadConfig();

      double distance = distanceService.getDistanceBetweenCities(start, end);
      double emissionFactorGrams = getEmissionFactor(transportMethod);
      double emissionFactorKg = emissionFactorGrams / 1000.0;
      double totalEmissions = distance * emissionFactorKg;
      double truncatedTotalEmissions = truncate(totalEmissions, 1);

      printResult(truncatedTotalEmissions);
    } catch (InvalidInputException | InvalidTransportationMethodException e) {
      userException(e, DEBUG);
    } catch (EmissionFactorsConfigException e) {
      emissionFactorsConfigException(e, DEBUG);
    } catch (ExternalAPIException e) {
      externalException(e, DEBUG);
    } catch (ORSTokenException e) {
      orsTokenException(e, DEBUG);
    } catch (Exception e) {
      unexpectedException(e, DEBUG);
    }
  }
}
