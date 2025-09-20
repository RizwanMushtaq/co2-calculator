package com.rizwanmushtaq.services.implementations;

import static com.rizwanmushtaq.utils.ExceptionMessages.SAME_START_END_CITY;

import com.rizwanmushtaq.exceptions.InvalidUserInputException;
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
    if (startCity.equals(endCity)) {
      throw new InvalidUserInputException(SAME_START_END_CITY);
    }

    Coordinate startCoordinates = apiService.getCityCoordinates(startCity);
    Coordinate endCoordinates = apiService.getCityCoordinates(endCity);

    return apiService.getDistanceBetweenCoordinates(startCoordinates, endCoordinates);
  }
}
