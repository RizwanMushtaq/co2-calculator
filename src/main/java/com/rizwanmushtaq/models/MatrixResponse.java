package com.rizwanmushtaq.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@ToString
@NoArgsConstructor
public class MatrixResponse {
  private double[][] distances;

  public double getDistance(int fromIndex, int toIndex) {
    return distances[fromIndex][toIndex];
  }
}
