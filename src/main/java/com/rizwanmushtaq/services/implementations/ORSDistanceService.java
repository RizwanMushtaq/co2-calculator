package com.rizwanmushtaq.services.implementations;

import com.rizwanmushtaq.models.Coordinate;
import com.rizwanmushtaq.services.DistanceService;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ORSDistanceService implements DistanceService {
  private final ORSAPIService apiService = new ORSAPIService();

  @Override
  public double getDistanceBetweenCities(String startCity, String endCity) {
    Coordinate startCoordinates = apiService.getCityCoordinates(startCity);
    Coordinate endCoordinates = apiService.getCityCoordinates(endCity);

    return apiService.getDistanceBetweenCoordinates(startCoordinates, endCoordinates);
  }
}
