package com.donkeys_today.server.application.user.dto.request;

public record UserSignUpRequest(

    String userName,
    String phoneNum,
    String email
) {
}
