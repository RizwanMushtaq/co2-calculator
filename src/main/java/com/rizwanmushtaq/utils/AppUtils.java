package com.rizwanmushtaq.utils;

import com.rizwanmushtaq.exceptions.ORSTokenException;

public class AppUtils {
  public static void printResult(double result) {
    System.out.println("Your trip caused " + result + "kg of CO2-equivalent.");
  }

  public static void checkORSToken(String token) {
    if (token == null || token.isEmpty()) {
      throw new ORSTokenException("ORS_TOKEN environment variable is not set.");
    }
  }
}
