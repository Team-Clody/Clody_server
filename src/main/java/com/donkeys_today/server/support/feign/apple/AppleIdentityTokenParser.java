package com.donkeys_today.server.support.feign.apple;

import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.UnauthorizedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class AppleIdentityTokenParser {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public Map<String, String> parseHeaders(String identityToken) {
        try {
            String encoded = identityToken.split("\\.")[0];
            String decoded = new String(Base64.getUrlDecoder().decode(encoded), StandardCharsets.UTF_8);
            return OBJECT_MAPPER.readValue(decoded, Map.class);
        } catch (JsonProcessingException | ArrayIndexOutOfBoundsException e) {
            throw new UnauthorizedException(ErrorType.INVALID_ID_TOKEN);
        }
    }

    public Claims parseWithPublicKeyAndGetClaims(String identityToken, PublicKey publicKey) {
        try {
            return getJwtParser(publicKey)
                    .parseClaimsJws(identityToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(ErrorType.EXPIRED_ID_TOKEN);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            throw new UnauthorizedException(ErrorType.INVALID_ID_TOKEN);
        }
    }

    private JwtParser getJwtParser(Key key) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
    }
}
