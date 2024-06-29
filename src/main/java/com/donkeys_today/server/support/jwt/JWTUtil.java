package com.donkeys_today.server.support.jwt;
import static com.donkeys_today.server.support.dto.type.ErrorType.EXPIRED_TOKEN;
import static com.donkeys_today.server.support.dto.type.ErrorType.INVALID_REFRESH_TOKEN;
import static com.donkeys_today.server.support.dto.type.ErrorType.INVALID_TOKEN;
import static com.donkeys_today.server.support.dto.type.ErrorType.UNKNOWN_TOKEN;
import static com.donkeys_today.server.support.dto.type.ErrorType.UNSUPPORTED_TOKEN;
import static com.donkeys_today.server.support.dto.type.ErrorType.WRONG_SIGNATURE_TOKEN;

import com.donkeys_today.server.support.exception.InternalServerException;
import com.donkeys_today.server.support.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JWTUtil {

    private final JwtTokenProvider jwtTokenProvider;

    private final RedisTemplate<String, String> redisTemplate;

    public void validateRefreshToken(String refreshToken) {
        validateToken(refreshToken);
        if (!redisTemplate.hasKey(getUserFromJwt(refreshToken))) {
            throw new UnauthorizedException(INVALID_REFRESH_TOKEN);
        }
    }

    public JwtValidationType validateToken(String token) {
        try {
            final Claims claims = getBody(token);
            if (claims.get(JWTConstants.TOKEN_TYPE).toString().equals(JWTConstants.ACCESS_TOKEN)) {
                return JwtValidationType.VALID_ACCESS;
            } else if (claims.get(JWTConstants.TOKEN_TYPE).toString().equals(JWTConstants.REFRESH_TOKEN)) {
                return JwtValidationType.VALID_REFRESH;
            }
            throw new UnauthorizedException(INVALID_TOKEN);
        } catch (MalformedJwtException e) {
            throw new UnauthorizedException(INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(EXPIRED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedException(UNKNOWN_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new UnauthorizedException(UNSUPPORTED_TOKEN);
        } catch (SignatureException e) {
            throw new UnauthorizedException(WRONG_SIGNATURE_TOKEN);
        }
    }

    public void deleteRefreshToken(String refreshToken) {

        validateToken(refreshToken);
        String userId = getUserFromJwt(refreshToken);

        if (redisTemplate.hasKey(userId)) {
            redisTemplate.delete(userId);
        } else {
            throw new InternalServerException();
        }
    }

    private Claims getBody(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtTokenProvider.getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserFromJwt(String token) {
        Claims claims = getBody(token);
        return claims.get(JWTConstants.USER_ID).toString();
    }

    public String getRoleFromJwt(String token) {
        Claims claims = getBody(token);
        return claims.get(JWTConstants.ROLE).toString();
    }

    public String getTypeFromJwt(String token) {
        Claims claims = getBody(token);
        return claims.get(JWTConstants.TOKEN_TYPE).toString();
    }
}