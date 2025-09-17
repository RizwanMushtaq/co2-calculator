package com.rizwanmushtaq.services.implementations;

import com.rizwanmushtaq.models.Coordinate;
import com.rizwanmushtaq.models.GeoJsonResponse;
import com.rizwanmushtaq.services.APIService;
import com.rizwanmushtaq.utils.JsonUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import static com.rizwanmushtaq.utils.AppConstants.GEOCODE_URL;
import static com.rizwanmushtaq.utils.AppConstants.ORS_TOKEN;

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

      GeoJsonResponse geo = JsonUtil.getMapper().readValue(response.body().string(),
          GeoJsonResponse.class);

      System.out.println(geo.getCoordinate());
      System.out.println(geo.getCityName());
      return geo.getCoordinate();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
