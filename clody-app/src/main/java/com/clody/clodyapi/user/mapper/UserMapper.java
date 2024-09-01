package com.clody.clodyapi.user.mapper;

import com.clody.clodyapi.user.controller.dto.request.UserSignInRequest;
import com.clody.clodyapi.user.controller.dto.request.UserSignUpRequest;
import com.clody.clodyapi.user.controller.dto.response.UserDeleteResponse;
import com.clody.clodyapi.user.controller.dto.response.UserInfoGetResponse;
import com.clody.clodyapi.user.controller.dto.response.UserNamePatchResponse;
import com.clody.domain.user.Platform;
import com.clody.domain.user.dto.UserDomainInfo;
import com.clody.domain.user.dto.UserInfo;

public class UserMapper {

  public static UserDomainInfo toDomainInfo(UserSignUpRequest request,
      String tokenWithBearer) {

    return UserDomainInfo.toUserDomainInfo(Platform.fromString(request.platform()), tokenWithBearer);
  }

  public static UserDomainInfo toDomainInfo(UserSignInRequest request,
                                            String tokenWithBearer) {

    return UserDomainInfo.toUserDomainInfo(Platform.fromString(request.platform()), tokenWithBearer);
  }

  public static UserInfoGetResponse toUserGetInfoResponse(UserInfo userinfo) {

    return UserInfoGetResponse.of(userinfo.email(), userinfo.name(), userinfo.platform());
  }

  public static UserDeleteResponse toUserDeleteResponse(UserInfo userinfo) {

    return UserDeleteResponse.of(userinfo.email(), userinfo.name());
  }


  public static UserNamePatchResponse toUserNamePatchResponse(UserInfo userinfo) {
    return UserNamePatchResponse.of(userinfo.name());
  }
}
