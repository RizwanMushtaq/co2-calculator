package com.rizwanmushtaq.utils;

import com.rizwanmushtaq.exceptions.ORSTokenException;

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
   * Check if ORS_TOKEN is set in environment variables
   * If not, throw ORSTokenException
   */
  public static void checkORSToken(String token) {
    if (token == null || token.isEmpty()) {
      throw new ORSTokenException("ORS_TOKEN environment variable is not set.");
    }
  }
}
