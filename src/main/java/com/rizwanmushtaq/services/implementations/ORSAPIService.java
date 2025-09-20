package com.rizwanmushtaq.services.implementations;

import static com.rizwanmushtaq.utils.AppConstants.*;
import static com.rizwanmushtaq.utils.EnvironmentVariablesProvider.ORS_TOKEN;
import static com.rizwanmushtaq.utils.ExceptionMessages.*;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rizwanmushtaq.exceptions.ExternalAPIException;
import com.rizwanmushtaq.exceptions.InvalidUserInputException;
import com.rizwanmushtaq.models.Coordinate;
import com.rizwanmushtaq.models.GeocodeSearchResponse;
import com.rizwanmushtaq.models.MatrixResponse;
import com.rizwanmushtaq.services.APIService;
import com.rizwanmushtaq.utils.ObjectMapperUtil;
import java.io.IOException;
import java.util.List;
import okhttp3.*;

public class ORSAPIService implements APIService {
  private final OkHttpClient client;
  private final String orsToken;

  public ORSAPIService() {
    this(new OkHttpClient(), ORS_TOKEN);
  }

  public ORSAPIService(OkHttpClient client, String orsToken) {
    this.orsToken = orsToken;
    this.client = client;
  }

  @Override
  public Coordinate getCityCoordinates(String city) {
    String urlWithParams = getCityCoordinatesUrl(city, LOCALITY, 1);
    Request request =
        new Request.Builder().addHeader(AUTHORIZATION, orsToken).url(urlWithParams).build();

    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        throw new ExternalAPIException(
            GET_CITY_COORDINATES_UNSUCCESSFUL
                + " - "
                + city
                + " - "
                + response.code()
                + " -"
                + response.body().string());
      }

      String responseBody = response.body().string();

      GeocodeSearchResponse geocodeSearchResponse =
          ObjectMapperUtil.getMapper().readValue(responseBody, GeocodeSearchResponse.class);

      Coordinate coordinate = geocodeSearchResponse.getCoordinate();

      if (coordinate == null) {
        throw new InvalidUserInputException(UNKNOWN_CITY_NAME + city);
      }

      return coordinate;
    } catch (IOException e) {
      throw new ExternalAPIException(
          GET_CITY_COORDINATES_FAILED + " - " + city + " - " + e.getMessage());
    }
  }

  @Override
  public double getDistanceBetweenCoordinates(Coordinate start, Coordinate end) {
    List<List<Double>> locations =
        List.of(
            List.of(start.longitude(), start.latitude()), List.of(end.longitude(), end.latitude()));

    ObjectNode objectNode = ObjectMapperUtil.getMapper().createObjectNode();
    objectNode.set(LOCATIONS, ObjectMapperUtil.getMapper().valueToTree(locations));
    objectNode.putArray(METRICS).add(DISTANCE);
    objectNode.put(UNITS, KM);

    RequestBody body = RequestBody.create(objectNode.toString(), MediaType.get(APPLICATION_JSON));

    Request request =
        new Request.Builder().url(MATRIX_URL).addHeader(AUTHORIZATION, orsToken).post(body).build();

    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        throw new ExternalAPIException(
            GET_DISTANCE_BETWEEN_COORDINATES_UNSUCCESSFUL
                + " - "
                + response.code()
                + " -"
                + response.body().string());
      }

      String responseBody = response.body().string();

      MatrixResponse matrix =
          ObjectMapperUtil.getMapper().readValue(responseBody, MatrixResponse.class);

      Double distance = matrix.getDistance(0, 1);
      if (distance == null || distance < 0) {
        throw new InvalidUserInputException(UNKNOWN_ROUTE);
      }

      return distance;
    } catch (IOException e) {
      throw new ExternalAPIException(
          GET_DISTANCE_BETWEEN_COORDINATES_FAILED + " - " + e.getMessage());
    }
  }

  private String getCityCoordinatesUrl(String city, String layer, int size) {
    return GEOCODE_URL + "?text=" + city + "&layers=" + layer + "&size" + "=" + size;
  }
}
