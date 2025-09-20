package com.rizwanmushtaq.utils;

/** Exception messages used in the application. */
public class ExceptionMessages {
  public static final String GET_CITY_COORDINATES_FAILED = "Failed to fetch coordinates for city.";
  public static final String GET_CITY_COORDINATES_UNSUCCESSFUL =
      "Unsuccessful response from API for city.";
  public static final String GET_DISTANCE_BETWEEN_COORDINATES_FAILED =
      "Failed to fetch distance from matrix API.";
  public static final String GET_DISTANCE_BETWEEN_COORDINATES_UNSUCCESSFUL =
      "Unsuccessful response from matrix API.";
  public static final String UNKNOWN_TRANSPORTATION_METHOD = "Unknown transportation method: ";
  public static final String UNKNOWN_CITY_NAME = "Unknown city name provided: ";
  public static final String UNKNOWN_ROUTE = "No route found between the provided locations.";
  public static final String SAME_START_END_CITY = "Start city and end city cannot be the same.";
  public static final String MISSING_ORS_TOKEN = "ORS_TOKEN environment variable is not set.";
  public static final String EMPTY_CITY_NAME = "City name cannot be empty.";
}
