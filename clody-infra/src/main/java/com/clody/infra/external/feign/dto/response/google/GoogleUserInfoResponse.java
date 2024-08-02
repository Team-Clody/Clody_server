package com.clody.infra.external.feign.dto.response.google;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GoogleUserInfoResponse(
        String sub,
        String name,
        String given_name,
        String family_name,
        String picture,
        String email,
        Boolean email_verified,
        String locale
) {
    public static GoogleUserInfoResponse of(String id, String name, String givenName, String familyName,
                                            String picture,
                                            String email, Boolean emailVerified, String locale) {
        return new GoogleUserInfoResponse(id, name, givenName, familyName, picture, email, emailVerified, locale);
    }
}
