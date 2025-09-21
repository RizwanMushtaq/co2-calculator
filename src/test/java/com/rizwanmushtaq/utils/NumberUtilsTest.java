package com.rizwanmushtaq.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class NumberUtilsTest {

  @Test
  void truncate_positiveNumber() {
    double value = 3.14159;
    double result = NumberUtils.truncate(value, 1);
    assertEquals(3.1, result);
  }

  @Test
  void truncate_negativeNumber() {
    double value = -3.4959;
    double result = NumberUtils.truncate(value, 1);
    assertEquals(-3.4, result);
  }

  @Test
  void truncate_zeroDigits() {
    double value = 123.456;
    double result = NumberUtils.truncate(value, 0);
    assertEquals(123.0, result);
  }

  @Test
  void truncate_zeroValue() {
    double value = 0.0;
    double result = NumberUtils.truncate(value, 3);
    assertEquals(0.0, result);
  }

  @Test
  void truncate_largeDigits() {
    double value = 1.2345678988;
    double result = NumberUtils.truncate(value, 8);
    assertEquals(1.23456789, result);
  }

  @Test
  void truncate_negativeDigits_throwsException() {
    double value = 1.23;
    assertThrows(IllegalArgumentException.class, () -> NumberUtils.truncate(value, -1));
  }

  @Test
  void truncate_truncatesWithoutRounding() {
    double value = 2.9999;
    double result = NumberUtils.truncate(value, 2);
    assertEquals(2.99, result);
  }
}
