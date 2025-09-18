package com.rizwanmushtaq;

import com.rizwanmushtaq.exceptions.*;
import com.rizwanmushtaq.services.implementations.ORSDistanceService;

import java.util.Map;

import static com.rizwanmushtaq.config.EmissionFactorsConfig.getEmissionFactor;
import static com.rizwanmushtaq.config.EmissionFactorsConfig.loadConfig;
import static com.rizwanmushtaq.utils.AppConstants.*;
import static com.rizwanmushtaq.utils.AppUtils.parseArgs;
import static com.rizwanmushtaq.utils.AppUtils.printResult;
import static com.rizwanmushtaq.utils.ExceptionMessages.MISSING_REQUIRED_PARAMETERS;
import static com.rizwanmushtaq.utils.ExitCodes.SUCCESS;
import static com.rizwanmushtaq.utils.NumberUtils.truncate;

public class App {
  private static final ORSDistanceService distanceService = new ORSDistanceService();

  public static void main(String[] args) {
    try {
      new App().run(args);
      System.exit(SUCCESS);
    } catch (InvalidInputException | InvalidTransportationMethodException e) {
      GlobalExceptionHandler.userException(e, DEBUG);
    } catch (EmissionFactorsConfigException e) {
      GlobalExceptionHandler.emissionFactorsConfigException(e, DEBUG);
    } catch (ExternalAPIException e) {
      GlobalExceptionHandler.externalException(e, DEBUG);
    } catch (Exception e) {
      GlobalExceptionHandler.unexpectedException(e, DEBUG);
    }
  }

  private void run(String[] args) {
    loadConfig();
    Map<String, String> params = parseArgs(args);
    String start = params.get(START);
    String end = params.get(END);
    String transportMethod = params.get(TRANSPORTATION_METHOD);

    if (start == null || end == null || transportMethod == null) {
      throw new InvalidInputException(MISSING_REQUIRED_PARAMETERS);
    }

    double distance = distanceService.getDistanceBetweenCities(start, end);
    double emissionFactorGrams = getEmissionFactor(transportMethod);
    double emissionFactorKg = emissionFactorGrams / 1000.0;
    double totalEmissions = distance * emissionFactorKg;
    double truncatedTotalEmissions = truncate(totalEmissions, 1);
    printResult(truncatedTotalEmissions);
  }
}
