package com.rizwanmushtaq.utils;

public class EnvironmentVariablesProvider {
  public static final String ORS_TOKEN = System.getenv("ORS_TOKEN");
  public static final boolean CO2_DEBUG =
      Boolean.parseBoolean(System.getenv().getOrDefault("CO2_DEBUG", "false"));
}
