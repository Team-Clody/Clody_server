package com.donkeys_today.server.support.security.config;

import java.util.List;

public class WhiteListConstants {

  public static final List<String> FILTER_WHITE_LIST = List.of(
      "/api/v1/auth",
      "/api/v1/kakao",
      "/oauth/authorize",
      "/actuator/health",
      "/error",
      "/swagger",
      "/redirect",
      "/swagger-ui/**",
      "/api-docs/**"
  );

  public static final String[] SECURITY_WHITE_LIST = {
      "/api/v1/kakao",
      "/api/v1/auth/**",
      "/actuator/health",
      "/swagger",
      "/swagger-ui/**",
      "/redirect",
      "/login/oauth2/code/kakao",
      "/v3/api-docs/**",

  };
}
