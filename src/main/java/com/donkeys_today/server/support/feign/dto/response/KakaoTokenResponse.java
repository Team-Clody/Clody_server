package com.donkeys_today.server.support.feign.dto.response;

public record KakaoTokenResponse(
    String accessToken
) {
  private static final String BEARER = "Bearer ";

  public static KakaoTokenResponse createAccessToken(String accessToken) {
    return new KakaoTokenResponse(accessToken);
  }

  public static String getTokenWithPrefix(String accessToken) {
    return BEARER + accessToken;
  }
}
