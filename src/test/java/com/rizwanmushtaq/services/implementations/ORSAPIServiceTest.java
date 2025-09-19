package com.rizwanmushtaq.services.implementations;

import com.rizwanmushtaq.exceptions.ExternalAPIException;
import com.rizwanmushtaq.models.Coordinate;
import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.rizwanmushtaq.utils.ExceptionMessages.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ORSAPIServiceTest {
  private static final String DUMMY_URL = "http://dummyURL";
  private static final String DUMMY_MESSAGE = "dummy message";
  private static final MediaType JSON_MEDIA_TYPE = MediaType.get("application/json");

  private OkHttpClient mockClient;
  private Call mockCall;
  private ORSAPIService orsapiService;

  @BeforeEach
  void setUp() {
    mockClient = mock(OkHttpClient.class);
    mockCall = mock(Call.class);
    orsapiService = new ORSAPIService(mockClient, "dummyToken") {
    };
  }

  private Response buildResponse(int code, String body) {
    return new Response.Builder()
        .code(code)
        .protocol(Protocol.HTTP_1_1)
        .message(DUMMY_MESSAGE)
        .request(
            new Request
                .Builder()
                .url(DUMMY_URL)
                .build())
        .body(ResponseBody.create(body, JSON_MEDIA_TYPE))
        .build();
  }

  @Test
  void testGetCityCoordinatesSuccess() throws IOException {
    String jsonBody = """
        {
          "features": [
            {
              "geometry": {
                "coordinates": [10.0, 20.0]
              }
            }
          ]
        }
        """;

    when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
    Response response = buildResponse(200, jsonBody);
    when(mockCall.execute()).thenReturn(response);
    Coordinate coordinate = orsapiService.getCityCoordinates("Berlin");

    assertNotNull(coordinate);
    assertEquals(10.0, coordinate.longitude());
    assertEquals(20.0, coordinate.latitude());
  }

  @Test
  void testGetCityCoordinatesUnsuccessful() throws Exception {
    when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
    when(mockCall.execute()).thenReturn(buildResponse(500, "Internal Error"));

    ExternalAPIException exception = assertThrows(
        ExternalAPIException.class,
        () -> orsapiService.getCityCoordinates("Berlin")
    );
    assertTrue(exception.getMessage().contains(GET_CITY_COORDINATES_UNSUCCESSFUL));
  }

  @Test
  void testGetCityCoordinatesThrowsIOException() throws Exception {
    when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
    when(mockCall.execute()).thenThrow(new IOException("network down"));

    ExternalAPIException ex = assertThrows(
        ExternalAPIException.class,
        () -> orsapiService.getCityCoordinates("Berlin")
    );

    assertTrue(ex.getMessage().contains(GET_CITY_COORDINATES_FAILED));
  }

  @Test
  void testGetDistanceBetweenCoordinatesSuccess() throws Exception {
    String jsonBody = """
        {
          "distances": [
            [0.0, 42.0],
            [42.0, 0.0]
          ]
        }
        """;

    when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
    when(mockCall.execute()).thenReturn(buildResponse(200, jsonBody));

    Coordinate start = new Coordinate(10.0, 20.0);
    Coordinate end = new Coordinate(30.0, 40.0);
    double distance = orsapiService.getDistanceBetweenCoordinates(start, end);

    assertEquals(42.0, distance);
  }

  @Test
  void testGetDistanceBetweenCoordinatesUnsuccessful() throws Exception {
    when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
    when(mockCall.execute()).thenReturn(buildResponse(400, "Bad Request"));

    Coordinate start = new Coordinate(10.0, 20.0);
    Coordinate end = new Coordinate(30.0, 40.0);

    ExternalAPIException ex = assertThrows(
        ExternalAPIException.class,
        () -> orsapiService.getDistanceBetweenCoordinates(start, end)
    );

    assertTrue(ex.getMessage().contains(GET_DISTANCE_BETWEEN_COORDINATES_UNSUCCESSFUL));
  }

  @Test
  void testGetDistanceBetweenCoordinatesFailure() throws Exception {
    when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
    when(mockCall.execute()).thenThrow(new IOException("network down"));

    Coordinate start = new Coordinate(10.0, 20.0);
    Coordinate end = new Coordinate(30.0, 40.0);

    ExternalAPIException ex = assertThrows(
        ExternalAPIException.class,
        () -> orsapiService.getDistanceBetweenCoordinates(start, end)
    );

    assertTrue(ex.getMessage().contains(GET_DISTANCE_BETWEEN_COORDINATES_FAILED));
  }
}

