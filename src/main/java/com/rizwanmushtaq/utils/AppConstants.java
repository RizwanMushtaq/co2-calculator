package com.rizwanmushtaq.utils;

/** Application constants */
public class AppConstants {
  public static final String GEOCODE_URL = "https://api.openrouteservice.org/geocode/search";
  public static final String MATRIX_URL = "https://api.openrouteservice.org/v2/matrix/driving-car";
  public static final String ORS_TOKEN = System.getenv("ORS_TOKEN");
  public static final boolean DEBUG =
      Boolean.parseBoolean(System.getenv().getOrDefault("CO2_DEBUG", "false"));
  public static final String AUTHORIZATION = "Authorization";
  public static final String APPLICATION_JSON = "application/json";
  public static final String LOCATIONS = "locations";
  public static final String METRICS = "metrics";
  public static final String DISTANCE = "distance";
  public static final String UNITS = "units";
  public static final String KM = "km";
  public static final String UNKNOWN = "unknown";
}
