package com.donkeys_today.server.support.feign.dto.response.apple;

import static com.donkeys_today.server.common.constants.Constants.BEARER;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AppleTokenResponse(
        String access_token,
        String refresh_token,
        String id_token
) {
    public static AppleTokenResponse of(String accessToken, String refresh_token, String idToken) {
        return new AppleTokenResponse(accessToken, refresh_token, idToken);
    }

    public static String getTokenWithPrefix(String accessToken) {
        return BEARER + accessToken;
    }
}