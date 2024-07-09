package com.donkeys_today.server.presentation.user.dto.requset;

public record UserSignInRequest(
        String platform,
        String id_token

) {

}
