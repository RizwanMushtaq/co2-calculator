package com.rizwanmushtaq.services.implementations;

import static com.rizwanmushtaq.utils.ExceptionMessages.EMPTY_CITY_NAMES;
import static com.rizwanmushtaq.utils.ExceptionMessages.SAME_START_END_CITY;

import com.rizwanmushtaq.exceptions.InvalidUserInputException;
import com.rizwanmushtaq.models.Coordinate;
import com.rizwanmushtaq.services.ApiService;
import com.rizwanmushtaq.services.DistanceService;

/** Calculates the distance between cities using ORS API. */
public class OrsDistanceService implements DistanceService {
  private final ApiService apiService;

  /** The default constructor. */
  public OrsDistanceService() {
    this(new OrsApiService());
  }

  /**
   * Creates a new instance of {@code OrsDistanceService} for testing purposes.
   *
   * @param apiService the API service implementation to use (typically a mock in tests)
   */
  public OrsDistanceService(ApiService apiService) {
    this.apiService = apiService;
  }

  @Override
  public double getDistanceBetweenCities(String startCity, String endCity) {
    if (startCity == null || endCity == null || startCity.isBlank() || endCity.isBlank()) {
      throw new InvalidUserInputException(EMPTY_CITY_NAMES);
    }

    if (startCity.equals(endCity)) {
      throw new InvalidUserInputException(SAME_START_END_CITY + " " + startCity);
    }

    Coordinate startCoordinates = apiService.getCityCoordinates(startCity);
    Coordinate endCoordinates = apiService.getCityCoordinates(endCity);

    return apiService.getDistanceBetweenCoordinates(startCoordinates, endCoordinates);
  }
}
