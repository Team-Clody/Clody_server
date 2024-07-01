package com.donkeys_today.server.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocialLoginPlatform{

  KAKAO("kakao"),
  APPLE("apple");

  private final String platform;

}
