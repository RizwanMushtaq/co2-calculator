package com.rizwanmushtaq.services.implementations;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rizwanmushtaq.exceptions.ExternalAPIException;
import com.rizwanmushtaq.models.Coordinate;
import com.rizwanmushtaq.models.GeocodeSearchResponse;
import com.rizwanmushtaq.models.MatrixResponse;
import com.rizwanmushtaq.services.APIService;
import com.rizwanmushtaq.utils.ObjectMapperUtil;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

import static com.rizwanmushtaq.utils.AppConstants.*;
import static com.rizwanmushtaq.utils.ExceptionMessages.*;

public class ORSAPIService implements APIService {
  private final OkHttpClient client = new OkHttpClient();

  @Override
  public Coordinate getCityCoordinates(String city) {
    String urlWithParams = getCityCoordinatesUrl(city);
    Request request = new Request.Builder()
        .url(urlWithParams)
        .build();

    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        throw new ExternalAPIException(
            GET_CITY_COORDINATES_UNSUCCESSFUL +
                " - " +
                city +
                " - " +
                response.code() +
                " -" +
                response.body().string()
        );
      }

      GeocodeSearchResponse geo = ObjectMapperUtil.getMapper().readValue(response.body().string(), GeocodeSearchResponse.class);

      return geo.getCoordinate();
    } catch (IOException e) {
      throw new ExternalAPIException(
          GET_CITY_COORDINATES_FAILED +
              " - " +
              city +
              " - " +
              e.getMessage()
      );
    }
  }

  @Override
  public double getDistanceBetweenCoordinates(Coordinate start, Coordinate end) {
    List<List<Double>> locations = List.of(
        List.of(start.longitude(), start.latitude()),
        List.of(end.longitude(), end.latitude())
    );

    ObjectNode objectNode = ObjectMapperUtil.getMapper().createObjectNode();
    objectNode.set(LOCATIONS, ObjectMapperUtil.getMapper().valueToTree(locations));
    objectNode.putArray(METRICS).add(DISTANCE);
    objectNode.put(UNITS, KM);

    RequestBody body = RequestBody.create(
        objectNode.toString(),
        MediaType.get(APPLICATION_JSON)
    );

    Request request = new Request.Builder()
        .url(MATRIX_URL)
        .addHeader(AUTHORIZATION, ORS_TOKEN)
        .post(body)
        .build();

    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        throw new ExternalAPIException(
            GET_DISTANCE_BETWEEN_COORDINATES_UNSUCCESSFUL +
                " - " +
                response.code() +
                " -" +
                response.body().string()
        );
      }

      MatrixResponse matrix = ObjectMapperUtil.getMapper().readValue(response.body().string(), MatrixResponse.class);
      return matrix.getDistance(0, 1);
    } catch (IOException e) {
      throw new ExternalAPIException(GET_DISTANCE_BETWEEN_COORDINATES_FAILED + " - " + e.getMessage());
    }
  }

  private String getCityCoordinatesUrl(String city) {
    return GEOCODE_URL + "?api_key=" + ORS_TOKEN + "&text=" + city + "&layers=locality" + "&size=1";
  }
}
