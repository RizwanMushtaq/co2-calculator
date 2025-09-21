package com.rizwanmushtaq.utils;

import static com.rizwanmushtaq.utils.EnvironmentVariablesProvider.CO2_DEBUG;

public class AppUtils {
  private static final String co2Debug = CO2_DEBUG;

  /**
   * Print the result to console
   *
   * @param result The CO2-equivalent result to print
   */
  public static void printResult(double result) {
    System.out.println("Your trip caused " + result + "kg of CO2-equivalent.");
  }

  /** Check if debug mode is set */
  public static boolean isDebugSet(String co2Debug) {
    return co2Debug != null && co2Debug.equals("true");
  }
}
