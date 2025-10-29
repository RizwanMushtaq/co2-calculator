package com.rizwanmushtaq.services;

import com.rizwanmushtaq.models.Coordinate;

/** Interface to get data for co2 calculations. */
public interface ApiService {
  /** get coordinates of a city. */
  Coordinate getCityCoordinates(String city);

  /**
   * get distance between coordinates of two cities.
   *
   * @param start -> start city coordinates
   * @param end -> end city coordinates
   * @return -> return double value.
   */
  double getDistanceBetweenCoordinates(Coordinate start, Coordinate end);
}
