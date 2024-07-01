package com.donkeys_today.server.application.auth.dto.response;

import com.donkeys_today.server.support.jwt.Token;
import lombok.Builder;

@Builder
public record SocialLoginResponse(
    String accessToken,
    String refreshToken
) {
  public static SocialLoginResponse from(Token token) {
    return SocialLoginResponse.builder()
        .accessToken(token.accessToken())
        .refreshToken(token.refreshToken())
        .build();
  }
}
