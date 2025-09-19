package com.rizwanmushtaq.services.implementations;

import static com.rizwanmushtaq.config.EmissionFactorsConfig.getEmissionFactor;
import static com.rizwanmushtaq.utils.NumberUtils.truncate;

import com.rizwanmushtaq.services.DistanceService;
import com.rizwanmushtaq.services.EmissionCalculatorService;

public class ORSEmissionCalculatorService implements EmissionCalculatorService {
  private final DistanceService distanceService;

  public ORSEmissionCalculatorService() {
    this(new ORSDistanceService());
  }

  public ORSEmissionCalculatorService(ORSDistanceService orsDistanceService) {
    this.distanceService = orsDistanceService;
  }

  @Override
  public double calculateEmissions(String startCity, String endCity, String transportMethod) {
    double distance = distanceService.getDistanceBetweenCities(startCity, endCity);
    double emissionFactorGrams = getEmissionFactor(transportMethod);
    double emissionFactorKg = emissionFactorGrams / 1000.0;
    return truncate(distance * emissionFactorKg, 1);
  }
}
