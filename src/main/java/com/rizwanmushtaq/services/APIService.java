package com.rizwanmushtaq.services;

import com.rizwanmushtaq.models.Coordinate;

public interface APIService {
  Coordinate getCityCoordinates(String city);

  Double getDistanceBetweenCoordinates(Coordinate start, Coordinate end);
}
