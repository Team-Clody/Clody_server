package com.donkeys_today.server.support.feign.apple;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppleIdentityTokenValidator {

    @Value("${apple.client-id}")
    private String clientId;

    public boolean isValidAppleIdentityToken(Claims claims) {
        return claims.getIssuer().contains("https://appleid.apple.com")
                && claims.getAudience().equals(clientId);
    }
}
