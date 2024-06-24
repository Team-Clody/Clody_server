package com.donkeys_today.server.application.user.dto.response;

import com.donkeys_today.server.domain.user.User;
import lombok.Builder;

@Builder
public record UserSignUpResponse(

    Long id,
    String userName,
    String phoneNum,
    String email
) {

  public static UserSignUpResponse from(User user) {
    return  UserSignUpResponse.builder()
        .id(user.getId())
        .userName(user.getUserName())
        .phoneNum(user.getPhoneNum())
        .email(user.getEmail())
        .build();
  }
}
