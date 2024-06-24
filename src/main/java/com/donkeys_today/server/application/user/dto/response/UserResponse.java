package com.donkeys_today.server.application.user.dto.response;

import com.donkeys_today.server.domain.user.User;
import lombok.Builder;


@Builder
public record UserResponse(

    Long id,
    String userName,
    String phoneNum,
    String email
) {


    public static UserResponse from(User user) {
      return  UserResponse.builder()
          .id(user.getId())
          .userName(user.getUserName())
          .phoneNum(user.getPhoneNum())
          .email(user.getEmail())
          .build();
    }
}
