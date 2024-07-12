package com.donkeys_today.server.presentation.auth.dto.response;

public record UserSignUpResponse(
        Long userId,
        String accessToken,
        String refreshToken
) {

    public static UserSignUpResponse of(Long userId, String accessToken, String refreshToken) {
        return new UserSignUpResponse(userId, accessToken, refreshToken);
    }
}
