package com.rizwanmushtaq.utils;

import java.util.HashMap;
import java.util.Map;

public class AppUtils {
  /**
   * Parse named parameters. Accepts both `--key value` and `--key=value`.
   */
  public static Map<String, String> parseArgs(String[] args) {
    Map<String, String> out = new HashMap<>();
    for (int i = 0; i < args.length; i++) {
      String a = args[i];
      if (!a.startsWith("--")) continue;
      String body = a.substring(2);
      if (body.contains("=")) {
        String[] parts = body.split("=", 2);
        out.put(parts[0], stripQuotes(parts[1]));
      } else {
        // next token if exists and not another key
        String val = null;
        if (i + 1 < args.length && !args[i + 1].startsWith("--")) {
          val = args[i + 1];
          i++;
        }
        out.put(body, stripQuotes(val));
      }
    }
    return out;
  }

  private static String stripQuotes(String s) {
    if (s == null) return null;
    if ((s.startsWith("\"") && s.endsWith("\"")) || (s.startsWith("'") && s.endsWith("'"))) {
      return s.substring(1, s.length() - 1);
    }
    return s;
  }

  public static void printResult(double result) {
    System.out.println("Your trip caused " + result + "kg of CO2-equivalent.");
  }
}
