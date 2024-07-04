package com.donkeys_today.server.presentation.user.dto.requset;

public record SocialLoginRequest(
    String socialLoginPlatform
) {
  public static SocialLoginRequest of(String socialLoginPlatform){
    return new SocialLoginRequest(socialLoginPlatform);
  }

}
