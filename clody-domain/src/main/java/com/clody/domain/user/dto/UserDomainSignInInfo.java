package com.clody.domain.user.dto;

import com.clody.domain.user.Platform;

public record UserDomainSignInInfo(
    Platform platform,
    String idToken,
    String authTokenWithBearer
) {

  public static UserDomainSignInInfo toSingUpInfo(Platform platform, String authTokenWithBearer) {
    return new UserDomainSignInInfo(platform, null, authTokenWithBearer);
  }

  public static UserDomainSignInInfo toSingUpInfo(Platform platform, String idToken, String authTokenWithBearer) {
    return new UserDomainSignInInfo(platform, idToken, authTokenWithBearer);
  }

}
