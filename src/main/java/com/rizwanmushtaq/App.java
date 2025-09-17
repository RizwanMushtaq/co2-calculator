package com.rizwanmushtaq;

import com.rizwanmushtaq.models.Coordinate;
import com.rizwanmushtaq.services.implementations.ORSAPIService;

import java.util.Map;

import static com.rizwanmushtaq.utils.AppUtils.parseArgs;

public class App {

  public static void main(String[] args) {
    System.out.println("Hello World!");
    run();

    try {
      Map<String, String> params = parseArgs(args);
      String start = params.get("start");
      String end = params.get("end");
      String transportKey = params.get("transportation-method");

      if (start == null || end == null || transportKey == null) {
        System.err.println("Missing required parameters. Usage: --start <city> --end <city> --transportation-method <method>");
        System.exit(2);
      }

      System.out.println(start + " to " + end + " by " + transportKey);
    } catch (Exception e) {
      System.err.println("Error parsing arguments: " + e.getMessage());
      System.exit(1);
    }
  }

  private static void run() {
    final ORSAPIService apiService = new ORSAPIService();
    final Coordinate city = apiService.getCityCoordinates("Munich");
    System.out.println("City: " + city);
  }
}
