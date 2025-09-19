package com.rizwanmushtaq.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

/** Utility class for managing a singleton ObjectMapper instance */
public class ObjectMapperUtil {
  @Getter
  private static final ObjectMapper mapper =
      new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
}
