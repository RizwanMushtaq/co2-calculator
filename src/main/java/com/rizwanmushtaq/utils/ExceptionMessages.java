package com.rizwanmushtaq.utils;

public class ExceptionMessages {
  public static final String MISSING_REQUIRED_PARAMETERS =
      "Missing required parameters. Usage: --start <city> --end <city> --transportation-method <method>";
  public static final String GET_CITY_COORDINATES_FAILED =
      "Failed to fetch coordinates for city.";
  public static final String GET_CITY_COORDINATES_UNSUCCESSFUL =
      "Unsuccessful response from API for city.";
  public static final String GET_DISTANCE_BETWEEN_COORDINATES_FAILED =
      "Failed to fetch distance from matrix API.";
  public static final String GET_DISTANCE_BETWEEN_COORDINATES_UNSUCCESSFUL =
      "Unsuccessful response from matrix API";
}
