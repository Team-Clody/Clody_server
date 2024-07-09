package com.donkeys_today.server.presentation.user.dto.requset;

public record UserSignUpRequest(
        String platform,

        String email,

        String name,

        String id_token

) {
}
