package com.rizwanmushtaq.models;

import static com.rizwanmushtaq.utils.ExceptionMessages.UNKNOWN_CITY_NAME;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rizwanmushtaq.exceptions.InvalidUserInputException;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@ToString
@NoArgsConstructor
public class GeocodeSearchResponse {
  private List<Feature> features;

  public Coordinate getCoordinate() {
    if (features == null || features.isEmpty()) {
      throw new InvalidUserInputException(UNKNOWN_CITY_NAME);
    }

    return new Coordinate(getLongitude(), getLatitude());
  }

  private double getLongitude() {
    return features.getFirst().getGeometry().getLongitude();
  }

  private double getLatitude() {
    return features.getFirst().getGeometry().getLatitude();
  }

  private String getCityName() {
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
