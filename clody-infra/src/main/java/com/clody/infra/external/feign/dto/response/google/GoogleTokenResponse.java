package com.clody.infra.external.feign.dto.response.google;

import com.clody.support.constants.HeaderConstants;
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
        return HeaderConstants.BEARER + accessToken;
    }
}
