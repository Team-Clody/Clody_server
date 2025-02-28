package com.clody.infra.external.feign.apple;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppleIdentityTokenValidator {

    @Value("${apple.client-id}")
    private String clientId;

    @Value("${apple.audience}")
    private String audience;


    public boolean isValidAppleIdentityToken(Claims claims) {
        return claims.getIssuer().contains(audience)
                && claims.getAudience().equals(clientId);
    }
}
