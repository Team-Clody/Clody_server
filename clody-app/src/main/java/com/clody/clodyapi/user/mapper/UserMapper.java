package com.clody.clodyapi.user.mapper;

import com.clody.clodyapi.user.controller.dto.request.UserSignInRequest;
import com.clody.clodyapi.user.controller.dto.request.UserSignUpRequest;
import com.clody.domain.user.Platform;
import com.clody.domain.user.dto.UserDomainInfo;

public class UserMapper {

  public static UserDomainInfo toDomainInfo(UserSignUpRequest request,
      String tokenWithBearer) {

    return UserDomainInfo.toUserDomainInfo(Platform.fromString(request.platform()), tokenWithBearer);
  }

  public static UserDomainInfo toDomainInfo(UserSignInRequest request,
                                            String tokenWithBearer) {

    return UserDomainInfo.toUserDomainInfo(Platform.fromString(request.platform()), tokenWithBearer);
  }

}
