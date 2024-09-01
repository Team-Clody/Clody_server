package com.clody.domain.user.dto;

import com.clody.domain.user.Platform;

public record UserInfo(
    String email,
    String name,
    Platform platform
) {

  public static UserInfo toUserInfo(String email, String name, Platform platform) {
    return new UserInfo(email, name, platform);
  }

}
