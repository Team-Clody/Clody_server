package com.donkeys_today.server.support.feign.dto.response;

public record KakaoTokenResponse(
    String accessToken,
    String refreshToken
) {
  public static KakaoTokenResponse of (String accessToken, String refreshToken) {
    return new KakaoTokenResponse(accessToken, refreshToken);
  }
}
