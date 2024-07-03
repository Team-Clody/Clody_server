package com.donkeys_today.server.support.security.config;

import java.util.List;

public class WhiteListConstants {

  public static final List<String> FILTER_WHITE_LIST = List.of(
      "/api/v1/auth/**",
      "/",
      "/api/v1/kakao",
      "/oauth/authorize",
      "/actuator/health",
      "/error",
      "/swagger-ui/",
      "/swagger-resources/",
      "/api-docs/"
  );

  public static final String[] SECURITY_WHITE_LIST = {
      "/api/v1/kakao",
      "/api/v1/auth/**",
      "/",
      "/actuator/health",
      "/api-docs/**",
      "swagger-ui/**"
  };
}
