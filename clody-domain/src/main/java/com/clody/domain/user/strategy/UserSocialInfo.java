package com.clody.domain.user.strategy;

import com.clody.domain.user.Platform;

public record UserSocialInfo(
    String id,
    Platform platform
) {
  public static UserSocialInfo of(String id, Platform platform) {
    return new UserSocialInfo(id, platform);
  }
}
