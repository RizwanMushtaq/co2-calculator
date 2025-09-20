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
  private Double[][] distances;

  public Double getDistance(int fromIndex, int toIndex) {
    if (distances == null
        || fromIndex < 0
        || toIndex < 0
        || fromIndex >= distances.length
        || toIndex >= distances.length) {
      return null;
    }
    return distances[fromIndex][toIndex];
  }
}
