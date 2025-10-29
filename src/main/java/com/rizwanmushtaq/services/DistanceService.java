package com.rizwanmushtaq.services;

/** It is to get distance between two cities. */
public interface DistanceService {
  /**
   * get the distance between two cities.
   *
   * @param startCity -> start city name.
   * @param endCity -> end city name.
   * @return -> ditance as double value.
   */
  double getDistanceBetweenCities(String startCity, String endCity);
}
