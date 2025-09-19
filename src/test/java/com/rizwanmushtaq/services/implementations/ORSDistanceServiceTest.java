package com.rizwanmushtaq.services.implementations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.rizwanmushtaq.exceptions.ExternalAPIException;
import com.rizwanmushtaq.models.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ORSDistanceServiceTest {
  private ORSAPIService mockApiService;
  private ORSDistanceService distanceService;

  @BeforeEach
  void setUp() {
    mockApiService = mock(ORSAPIService.class);
    distanceService = new ORSDistanceService(mockApiService);
  }

  @Test
  void testGetDistanceBetweenCitiesSuccess() {
    Coordinate berlin = new Coordinate(13.4050, 52.5200);
    Coordinate hamburg = new Coordinate(9.9937, 53.5511);

    when(mockApiService.getCityCoordinates("Berlin")).thenReturn(berlin);
    when(mockApiService.getCityCoordinates("Hamburg")).thenReturn(hamburg);
    when(mockApiService.getDistanceBetweenCoordinates(berlin, hamburg)).thenReturn(289.0);

    double distance = distanceService.getDistanceBetweenCities("Berlin", "Hamburg");

    assertEquals(289.0, distance);
    verify(mockApiService).getCityCoordinates("Berlin");
    verify(mockApiService).getCityCoordinates("Hamburg");
    verify(mockApiService).getDistanceBetweenCoordinates(berlin, hamburg);
  }

  @Test
  void testGetDistanceBetweenCitiesWhenApiThrowsException() {
    when(mockApiService.getCityCoordinates("Berlin"))
        .thenThrow(new ExternalAPIException("network down"));

    ExternalAPIException ex =
        assertThrows(
            ExternalAPIException.class,
            () -> distanceService.getDistanceBetweenCities("Berlin", "Hamburg"));

    assertEquals("network down", ex.getMessage());

    verify(mockApiService).getCityCoordinates("Berlin");
    verify(mockApiService, never()).getCityCoordinates("Hamburg");
    verify(mockApiService, never()).getDistanceBetweenCoordinates(any(), any());
  }
}
