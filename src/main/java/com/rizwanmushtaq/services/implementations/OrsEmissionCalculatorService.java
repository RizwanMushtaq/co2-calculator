package com.rizwanmushtaq.services.implementations;

import static com.rizwanmushtaq.config.EmissionFactorsConfig.getEmissionFactor;
import static com.rizwanmushtaq.utils.NumberUtils.truncate;

import com.rizwanmushtaq.services.DistanceService;
import com.rizwanmushtaq.services.EmissionCalculatorService;

/** Calculates Emissions using ORS Api. */
public class OrsEmissionCalculatorService implements EmissionCalculatorService {
  private final DistanceService distanceService;

  /** It is a default constructor. */
  public OrsEmissionCalculatorService() {
    this(new OrsDistanceService());
  }

  /**
   * Creates a new instance of {@code OrsEmissionCalculatorService} for testing purposes.
   *
   * @param orsDistanceService -> implemtation of DistanceService interface.
   */
  public OrsEmissionCalculatorService(OrsDistanceService orsDistanceService) {
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
