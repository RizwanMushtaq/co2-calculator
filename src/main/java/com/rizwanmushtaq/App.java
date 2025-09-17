package com.rizwanmushtaq;

import com.rizwanmushtaq.config.EmissionFactorsConfig;
import com.rizwanmushtaq.services.implementations.ORSDistanceService;
import com.rizwanmushtaq.utils.AppUtils;
import com.rizwanmushtaq.utils.NumberUtils;

import java.util.Map;

import static com.rizwanmushtaq.utils.AppUtils.parseArgs;

public class App {
  private static final ORSDistanceService distanceService = new ORSDistanceService();

  public static void main(String[] args) {

    try {
      Map<String, String> params = parseArgs(args);
      String start = params.get("start");
      String end = params.get("end");
      String transportKey = params.get("transportation-method");

      if (start == null || end == null || transportKey == null) {
        System.err.println("Missing required parameters. Usage: --start <city> --end <city> --transportation-method <method>");
        System.exit(2);
      }

      double distance = distanceService.getDistanceBetweenCities(start, end);
      double emissionFactor =
          EmissionFactorsConfig.getEmissionFactor(transportKey);
      double totalEmissions = distance * emissionFactor;
      double truncatedTotalEmissions = NumberUtils.truncate(totalEmissions, 1);
      AppUtils.printResult(truncatedTotalEmissions);
    } catch (Exception e) {
      System.err.println("Error parsing arguments: " + e.getMessage());
      System.exit(1);
    }
  }
}
