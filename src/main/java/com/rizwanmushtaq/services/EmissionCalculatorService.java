package com.rizwanmushtaq.services;

/** It is to be used as entry point of c02 calculations. */
public interface EmissionCalculatorService {
  /**
   * it calculates co2 emissions.
   *
   * @param startCity -> start city name
   * @param endCity -> end city name.
   * @param transportMethod -> medium of trnasport.
   * @return -> co2 emissions as double value.
   */
  double calculateEmissions(String startCity, String endCity, String transportMethod);
}
