package com.donkeys_today.server.support.jwt;

import static com.donkeys_today.server.support.dto.type.ErrorType.EXPIRED_TOKEN;
import static com.donkeys_today.server.support.dto.type.ErrorType.INVALID_TOKEN;
import static com.donkeys_today.server.support.dto.type.ErrorType.UNKNOWN_TOKEN;
import static com.donkeys_today.server.support.dto.type.ErrorType.UNSUPPORTED_TOKEN;
import static com.donkeys_today.server.support.dto.type.ErrorType.WRONG_SIGNATURE_TOKEN;
import com.donkeys_today.server.support.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String JWT_SECRET;
    public String issueAccessToken(Long userId, String role) {
        return generateToken(JWTConstants.ACCESS_TOKEN, userId, role, JWTConstants.ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String issueRefreshToken(Long userId, String role) {
        return generateToken(JWTConstants.REFRESH_TOKEN, userId, role, JWTConstants.REFRESH_TOKEN_EXPIRATION_TIME);
    }

    public String generateToken(String type, Long userId, String role, Long tokenExpirationTime) {
        final Date now = new Date();
        final Claims claims = Jwts.claims()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenExpirationTime));      // 만료 시간

        claims.put(JWTConstants.TOKEN_TYPE, type);
        claims.put(JWTConstants.USER_ID, String.valueOf(userId));
        claims.put(JWTConstants.ROLE, role);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // Header
                .setClaims(claims) // Claim
                .signWith(getSigningKey()) // Signature
                .compact();
    }

    public SecretKey getSigningKey() {
        String encodedKey = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes()); //SecretKey 통해 서명 생성
        return Keys.hmacShaKeyFor(
                encodedKey.getBytes());
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

    private Claims getBody(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
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