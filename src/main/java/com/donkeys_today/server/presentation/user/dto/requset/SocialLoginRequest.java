package com.donkeys_today.server.presentation.user.dto.requset;

import com.donkeys_today.server.domain.user.SocialLoginPlatform;

public record SocialLoginRequest(
    SocialLoginPlatform socialLoginPlatform,
    String AuthorizationCode
) {
  public static SocialLoginRequest of(SocialLoginPlatform socialLoginPlatform, String code){
    return new SocialLoginRequest(socialLoginPlatform, code);
  }

}
