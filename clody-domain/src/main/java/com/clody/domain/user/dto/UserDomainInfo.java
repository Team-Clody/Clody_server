package com.clody.domain.user.dto;

import com.clody.domain.user.Platform;

public record UserDomainInfo(
    Platform platform,
    String tokenWithBearer
) {

  public static UserDomainInfo toUserDomainInfo(Platform platform, String tokenWithBearer) {
    return new UserDomainInfo(platform, tokenWithBearer);
  }

}
