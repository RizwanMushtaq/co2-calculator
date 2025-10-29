package com.rizwanmushtaq.services.implementations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.rizwanmushtaq.config.EmissionFactorsConfig;
import com.rizwanmushtaq.exceptions.ExternalAPIException;
import com.rizwanmushtaq.utils.NumberUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class OrsEmissionCalculatorServiceTest {
  private OrsDistanceService mockDistanceService;
  private OrsEmissionCalculatorService emissionCalculatorService;

  @BeforeEach
  void setUp() {
    mockDistanceService = mock(OrsDistanceService.class);
    emissionCalculatorService = new OrsEmissionCalculatorService(mockDistanceService);
  }

  @Test
  void testCalculateEmissionsSuccess() {
    when(mockDistanceService.getDistanceBetweenCities("Berlin", "Hamburg")).thenReturn(300.0);

    try (MockedStatic<EmissionFactorsConfig> emissionFactorMock =
            mockStatic(EmissionFactorsConfig.class);
        MockedStatic<NumberUtils> numberUtilsMock = mockStatic(NumberUtils.class)) {

      emissionFactorMock
          .when(() -> EmissionFactorsConfig.getEmissionFactor("diesel-car-small"))
          .thenReturn(180.0); // 180 g/km
      numberUtilsMock.when(() -> NumberUtils.truncate(54.0, 1)).thenReturn(54.0);

      double emissions =
          emissionCalculatorService.calculateEmissions("Berlin", "Hamburg", "diesel-car-small");

      assertEquals(54.0, emissions);

      verify(mockDistanceService).getDistanceBetweenCities("Berlin", "Hamburg");
      emissionFactorMock.verify(() -> EmissionFactorsConfig.getEmissionFactor("diesel-car-small"));
      numberUtilsMock.verify(() -> NumberUtils.truncate(54.0, 1));
    }
  }

  @Test
  void testCalculateEmissionsWhenDistanceServiceFails() {
    when(mockDistanceService.getDistanceBetweenCities("Berlin", "Hamburg"))
        .thenThrow(new ExternalAPIException("network down"));

    ExternalAPIException ex =
        assertThrows(
            ExternalAPIException.class,
            () ->
                emissionCalculatorService.calculateEmissions(
                    "Berlin", "Hamburg", "diesel-car-small"));

    assertEquals("network down", ex.getMessage());

    // Verify it never calls static methods because it failed early
    try (MockedStatic<EmissionFactorsConfig> emissionFactorMock =
            mockStatic(EmissionFactorsConfig.class);
        MockedStatic<NumberUtils> numberUtilsMock = mockStatic(NumberUtils.class)) {

      emissionFactorMock.verifyNoInteractions();
      numberUtilsMock.verifyNoInteractions();
    }
  }
}
