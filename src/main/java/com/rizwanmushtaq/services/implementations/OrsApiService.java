package com.rizwanmushtaq.services.implementations;

import static com.rizwanmushtaq.utils.AppConstants.APPLICATION_JSON;
import static com.rizwanmushtaq.utils.AppConstants.AUTHORIZATION;
import static com.rizwanmushtaq.utils.AppConstants.DISTANCE;
import static com.rizwanmushtaq.utils.AppConstants.GEOCODE_URL;
import static com.rizwanmushtaq.utils.AppConstants.KM;
import static com.rizwanmushtaq.utils.AppConstants.LOCALITY;
import static com.rizwanmushtaq.utils.AppConstants.LOCATIONS;
import static com.rizwanmushtaq.utils.AppConstants.MATRIX_URL;
import static com.rizwanmushtaq.utils.AppConstants.METRICS;
import static com.rizwanmushtaq.utils.AppConstants.UNITS;
import static com.rizwanmushtaq.utils.EnvironmentVariablesProvider.ORS_TOKEN;
import static com.rizwanmushtaq.utils.ExceptionMessages.EMPTY_CITY_NAME;
import static com.rizwanmushtaq.utils.ExceptionMessages.GET_CITY_COORDINATES_FAILED;
import static com.rizwanmushtaq.utils.ExceptionMessages.GET_CITY_COORDINATES_UNSUCCESSFUL;
import static com.rizwanmushtaq.utils.ExceptionMessages.GET_DISTANCE_BETWEEN_COORDINATES_FAILED;
import static com.rizwanmushtaq.utils.ExceptionMessages.GET_DISTANCE_BETWEEN_COORDINATES_UNSUCCESSFUL;
import static com.rizwanmushtaq.utils.ExceptionMessages.UNKNOWN_CITY_NAME;
import static com.rizwanmushtaq.utils.ExceptionMessages.UNKNOWN_ROUTE;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rizwanmushtaq.exceptions.ExternalAPIException;
import com.rizwanmushtaq.exceptions.InvalidUserInputException;
import com.rizwanmushtaq.models.Coordinate;
import com.rizwanmushtaq.models.GeocodeSearchResponse;
import com.rizwanmushtaq.models.MatrixResponse;
import com.rizwanmushtaq.services.ApiService;
import com.rizwanmushtaq.utils.ObjectMapperUtil;
import java.io.IOException;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/** Implementation of ApiService using ORS API. */
public class OrsApiService implements ApiService {
  private final String orsToken;

  /** It is a default constructor. */
  public OrsApiService() {
    this(ORS_TOKEN);
  }

  /**
   * Create a new Instance of OrsApiService for testing purpose.
   *
   * @param orsToken -> token to be used to access ors api.
   */
  public OrsApiService(String orsToken) {
    this.orsToken = orsToken;
  }

  @Override
  public Coordinate getCityCoordinates(String city) {
    if (city == null || city.isBlank()) {
      throw new InvalidUserInputException(EMPTY_CITY_NAME);
    }

    String urlWithParams = getCityCoordinatesUrl(city);
    Request request =
        new Request.Builder().addHeader(AUTHORIZATION, orsToken).url(urlWithParams).build();
    OkHttpClient client = createClient();

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
    } catch (Exception e) {
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
    OkHttpClient client = createClient();

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

  private String getCityCoordinatesUrl(String city) {
    return GEOCODE_URL + "?text=" + city + "&layers=" + LOCALITY + "&size" + "=" + 1;
  }

  /**
   * package scoped Http Client for testing purpose.
   *
   * @return -> new Http Client.
   */
  protected OkHttpClient createClient() {
    return new OkHttpClient();
  }
}
