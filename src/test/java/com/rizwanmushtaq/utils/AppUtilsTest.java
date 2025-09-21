package com.rizwanmushtaq.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class AppUtilsTest {

  @Test
  void printResult_shouldPrintExpectedMessage() {
    // Backup original System.out
    PrintStream originalOut = System.out;

    // Capture output
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    try {
      double result = 12.34;
      AppUtils.printResult(result);

      String expected = "Your trip caused 12.34kg of CO2-equivalent." + System.lineSeparator();
      assertEquals(expected, outputStream.toString());
    } finally {
      // Restore original System.out
      System.setOut(originalOut);
    }
  }

  @Test
  void isDebugSet_shouldReturnTrueWhenEnvIsTrue() {
    assertTrue(AppUtils.isDebugSet("true"));
  }

  @Test
  void isDebugSet_shouldReturnFalseWhenEnvIsFalse() {
    assertFalse(AppUtils.isDebugSet("false"));
  }

  @Test
  void isDebugSet_shouldReturnFalseWhenEnvIsNull() {
    assertFalse(AppUtils.isDebugSet(null));
  }
}
