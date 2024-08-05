package com.clody.domain.user.dto;

import com.clody.domain.user.Platform;

public record UserDomainInfo(
    Platform platform,
    String idToken,
    String authTokenWithBearer
) {

  public static UserDomainInfo toUserDomainInfo(Platform platform, String authTokenWithBearer) {
    return new UserDomainInfo(platform,null, authTokenWithBearer);
  }

  public static UserDomainInfo toUserDomainInfo(Platform platform, String idToken, String authTokenWithBearer) {
    return new UserDomainInfo(platform, idToken
        , authTokenWithBearer);
  }
}
