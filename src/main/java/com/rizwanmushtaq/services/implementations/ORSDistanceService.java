package com.rizwanmushtaq.services.implementations;

import com.rizwanmushtaq.models.Coordinate;
import com.rizwanmushtaq.services.DistanceService;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ORSDistanceService implements DistanceService {
  private final ORSAPIService apiService = new ORSAPIService();

  @Override
  public double getDistanceBetweenCities(String startCity, String endCity) {
    Coordinate start = apiService.getCityCoordinates(startCity);
    Coordinate end = apiService.getCityCoordinates(endCity);

    if (start != null && end != null) {
      return apiService.getDistanceBetweenCoordinates(start, end);
    }
    return 0;
  }
}
