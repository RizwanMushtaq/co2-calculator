package com.rizwanmushtaq.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@ToString
@NoArgsConstructor
public class GeocodeSearchResponse {
  @JsonProperty private List<Feature> features;

  public Coordinate getCoordinate() {
    if (features == null || features.isEmpty()) {
      return null;
    }

    double longitude = features.getFirst().getGeometry().getLongitude();
    double latitude = features.getFirst().getGeometry().getLatitude();
    return new Coordinate(longitude, latitude);
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  @Getter
  @ToString
  @NoArgsConstructor
  private static class Feature {
    @JsonProperty private Geometry geometry;
    @JsonProperty private Properties properties;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  @Getter
  @ToString
  @NoArgsConstructor
  private static class Geometry {
    @JsonProperty private List<Double> coordinates;

    public double getLongitude() {
      return coordinates.getFirst();
    }

    public double getLatitude() {
      return coordinates.get(1);
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  @Getter
  @ToString
  @NoArgsConstructor
  private static class Properties {
    @JsonProperty private String name = null;
  }
}
