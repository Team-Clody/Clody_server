package com.donkeys_today.server.presentation.auth.dto.response;

public record UserSignInResponse(
    Long userId,
    String accessToken,
    String refreshToken
) {
  public static UserSignInResponse of(Long userId, String accessToken, String refreshToken){
    return new UserSignInResponse(userId, accessToken, refreshToken);
  }
}
