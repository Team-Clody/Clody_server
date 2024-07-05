package com.donkeys_today.server.support.feign.dto.response.google;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GoogleTokenResponse(
        String accessToken,
        String refreshToken
) {
    public static GoogleTokenResponse of(String accessToken, String refreshToken) {
        return new GoogleTokenResponse(accessToken, refreshToken);
    }
}