package com.clody.clodyapi.user.controller.dto.response;

public record UserAuthResponse(
    Long userId,
    String accessToken,
    String refreshToken
) {

  public static UserAuthResponse of(Long userId, String accessToken, String refreshToken){
    return new UserAuthResponse(userId, accessToken, refreshToken);
  }

}
