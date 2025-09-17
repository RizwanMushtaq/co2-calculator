package com.rizwanmushtaq.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@ToString
@NoArgsConstructor
public class GeocodeSearchResponse {
  private List<Feature> features;

  public Coordinate getCoordinate() {
    return new Coordinate(getLongitude(), getLatitude());
  }

  public double getLongitude() {
    return features.getFirst().getGeometry().getLongitude();
  }

  public double getLatitude() {
    return features.getFirst().getGeometry().getLatitude();
  }

  public String getCityName() {
    return features.getFirst().getName();
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  @Getter
  @ToString
  @NoArgsConstructor
  private static class Feature {
    private Geometry geometry;
    private Properties properties;

    public String getName() {
      return properties.getName();
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  @Getter
  @ToString
  @NoArgsConstructor
  private static class Geometry {
    private List<Double> coordinates;

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
    private String name;
  }
}
