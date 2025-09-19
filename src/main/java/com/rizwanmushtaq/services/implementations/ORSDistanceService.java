package com.rizwanmushtaq.services.implementations;

import com.rizwanmushtaq.models.Coordinate;
import com.rizwanmushtaq.services.DistanceService;

public class ORSDistanceService implements DistanceService {
  private final ORSAPIService apiService;

  public ORSDistanceService() {
    this(new ORSAPIService());
  }

  public ORSDistanceService(ORSAPIService apiService) {
    this.apiService = apiService;
  }

  @Override
  public double getDistanceBetweenCities(String startCity, String endCity) {
    Coordinate startCoordinates = apiService.getCityCoordinates(startCity);
    Coordinate endCoordinates = apiService.getCityCoordinates(endCity);

    return apiService.getDistanceBetweenCoordinates(startCoordinates, endCoordinates);
  }
}
