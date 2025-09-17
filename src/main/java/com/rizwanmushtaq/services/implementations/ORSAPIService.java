package com.rizwanmushtaq.services.implementations;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rizwanmushtaq.models.Coordinate;
import com.rizwanmushtaq.models.GeocodeSearchResponse;
import com.rizwanmushtaq.models.MatrixResponse;
import com.rizwanmushtaq.services.APIService;
import com.rizwanmushtaq.utils.JsonUtil;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

import static com.rizwanmushtaq.utils.AppConstants.*;

public class ORSAPIService implements APIService {
  private final OkHttpClient client = new OkHttpClient();

  @Override
  public Coordinate getCityCoordinates(String city) {
    String urlWithParams =
        GEOCODE_URL + "?api_key=" + ORS_TOKEN + "&text=" + city + "&layers=locality" + "&size=1";
    Request request = new Request.Builder()
        .url(urlWithParams)
        .build();

    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        throw new IOException("Unexpected code " + response);
      }

      GeocodeSearchResponse geo = JsonUtil.getMapper().readValue(response.body().string(),
          GeocodeSearchResponse.class);

      return geo.getCoordinate();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Double getDistanceBetweenCoordinates(Coordinate start, Coordinate end) {
    List<List<Double>> locations = List.of(
        List.of(start.longitude(), start.latitude()),
        List.of(end.longitude(), end.latitude())
    );

    ObjectNode json = JsonUtil.getMapper().createObjectNode();
    json.set("locations", JsonUtil.getMapper().valueToTree(locations));
    json.putArray("metrics").add("distance");
    json.put("units", "km");

    RequestBody body = RequestBody.create(
        json.toString(),
        MediaType.get("application/json")
    );

    Request request = new Request.Builder()
        .url(MATRIX_URL)
        .addHeader("Authorization", ORS_TOKEN)
        .post(body)
        .build();

    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        throw new IOException("Unexpected code " + response);
      }

      MatrixResponse matrix = JsonUtil.getMapper().readValue(response.body().string(), MatrixResponse.class);
      return matrix.getDistance(0, 1);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
