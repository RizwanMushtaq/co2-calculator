package com.rizwanmushtaq.utils;

import static com.rizwanmushtaq.utils.EnvironmentVariablesProvider.CO2_DEBUG;

public class AppUtils {
  /**
   * Print the result to console
   *
   * @param result The CO2-equivalent result to print
   */
  public static void printResult(double result) {
    System.out.println("Your trip caused " + result + "kg of CO2-equivalent.");
  }

  /**
   * Check if debug mode is set via environment variable If CO2_DEBUG is set to "true", debug mode
   * is enabled Otherwise, debug mode is disabled
   */
  public static boolean isDebugSet() {
    return CO2_DEBUG != null && CO2_DEBUG.equals("true");
  }
}
