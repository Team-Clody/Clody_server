package com.donkeys_today.server.presentation.auth.dto.response;

public record TokenReissueResponse(
        String accessToken,
        String refreshToken
) {
    public static TokenReissueResponse of(String accessToken, String refreshToken) {
        return new TokenReissueResponse(accessToken, refreshToken);
    }
}
