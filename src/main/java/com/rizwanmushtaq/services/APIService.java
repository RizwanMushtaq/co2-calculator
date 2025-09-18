package com.rizwanmushtaq.services;

import com.rizwanmushtaq.models.Coordinate;

public interface APIService {
  Coordinate getCityCoordinates(String city);

  double getDistanceBetweenCoordinates(Coordinate start, Coordinate end);
}
