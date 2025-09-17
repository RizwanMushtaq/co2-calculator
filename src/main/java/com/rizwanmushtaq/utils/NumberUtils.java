package com.rizwanmushtaq.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtils {
  /**
   * Truncates a double to the given number of decimal places (without rounding).
   *
   * @param value  the number to truncate
   * @param digits number of digits after the decimal point
   * @return truncated double
   */
  public static double truncate(double value, int digits) {
    if (digits < 0) {
      throw new IllegalArgumentException("Digits must be non-negative");
    }
    return BigDecimal.valueOf(value)
        .setScale(digits, RoundingMode.DOWN) // always truncates toward zero
        .doubleValue();
  }
}
