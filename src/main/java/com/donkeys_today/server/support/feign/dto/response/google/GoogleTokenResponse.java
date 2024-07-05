package com.donkeys_today.server.support.feign.dto.response.google;

import static com.donkeys_today.server.common.constants.Constants.BEARER;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GoogleTokenResponse(
        String access_token,
        String id_token
) {
    public static GoogleTokenResponse of(String accessToken, String idToken) {
        return new GoogleTokenResponse(accessToken, idToken);
    }

    public static String getTokenWithPrefix(String accessToken) {
        return BEARER + accessToken;
    }
}