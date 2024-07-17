package com.donkeys_today.server.application.auth;

import static com.donkeys_today.server.support.dto.type.ErrorType.EXPIRED_TOKEN;
import static com.donkeys_today.server.support.dto.type.ErrorType.INVALID_REFRESH_TOKEN;
import static com.donkeys_today.server.support.dto.type.ErrorType.INVALID_TOKEN;
import static com.donkeys_today.server.support.dto.type.ErrorType.MISMATCH_REFRESH_TOKEN;
import static com.donkeys_today.server.support.dto.type.ErrorType.UNKNOWN_TOKEN;
import static com.donkeys_today.server.support.dto.type.ErrorType.UNSUPPORTED_TOKEN;
import static com.donkeys_today.server.support.dto.type.ErrorType.WRONG_SIGNATURE_TOKEN;
import static com.donkeys_today.server.support.jwt.JwtConstants.REFRESH_TOKEN_PREFIX;

import com.donkeys_today.server.common.constants.Constants;
import com.donkeys_today.server.presentation.auth.dto.response.TokenReissueResponse;
import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.UnauthorizedException;
import com.donkeys_today.server.support.jwt.JwtConstants;
import com.donkeys_today.server.support.jwt.JwtProvider;
import com.donkeys_today.server.support.jwt.JwtValidationType;
import com.donkeys_today.server.support.jwt.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtProviderImpl implements JwtProvider {

    private final SecretKey secretKey;
    private final RefreshTokenRepository refreshTokenRepository;
    private final String JWT_SECRET;

    public JwtProviderImpl(@Value("${jwt.secret}") String secret, RefreshTokenRepository refreshTokenRepository) {
        this.JWT_SECRET = secret;
        this.refreshTokenRepository = refreshTokenRepository;
        this.secretKey = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    }

    @Override
    public String issueAccessToken(Long userId) {
        return generateToken(JwtConstants.ACCESS_TOKEN, userId,
                JwtConstants.ACCESS_TOKEN_EXPIRATION_TIME);
    }

    @Override
    public String issueRefreshToken(Long userId) {
        String refreshToken = generateToken(JwtConstants.REFRESH_TOKEN, userId,
                JwtConstants.REFRESH_TOKEN_EXPIRATION_TIME);
        refreshTokenRepository.saveRefreshToken(userId, refreshToken,
                JwtConstants.REFRESH_TOKEN_EXPIRATION_TIME);
        return refreshToken;
    }

    public String generateToken(String type, Long userId, Long tokenExpirationTime) {
        final Date now = new Date();
        final Claims claims = Jwts.claims()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenExpirationTime));      // 만료 시간

        claims.put(JwtConstants.TOKEN_TYPE, type);
        claims.put(JwtConstants.USER_ID, userId);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(secretKey)
                .compact();
    }

    @Override
    public void validateAccessToken(String accessToken) {
        validateToken(accessToken);
    }

    @Override
    public void validateRefreshToken(String refreshToken) {
        validateToken(refreshToken);
        final Long id = getUserIdFromJwtSubject(refreshToken);
        final String key = REFRESH_TOKEN_PREFIX + id;
        if (!refreshTokenRepository.hasRefreshToken(key)) {
            throw new UnauthorizedException(INVALID_REFRESH_TOKEN);
        }
        if (!equalsRefreshToken(refreshToken, refreshTokenRepository.getRefreshToken(key))) {
            throw new UnauthorizedException(MISMATCH_REFRESH_TOKEN);
        }

    }

    @Override
    public boolean equalsRefreshToken(String refreshToken, String savedRefreshToken) {
        return refreshToken.equals(savedRefreshToken);
    }

    public JwtValidationType validateToken(String token) {
        try {
            final Claims claims = getBodyFromJwt(token);
            if (claims.get(JwtConstants.TOKEN_TYPE).toString().equals(JwtConstants.ACCESS_TOKEN)) {
                return JwtValidationType.VALID_ACCESS;
            } else if (claims.get(JwtConstants.TOKEN_TYPE).toString().equals(JwtConstants.REFRESH_TOKEN)) {
                return JwtValidationType.VALID_REFRESH;
            }
            throw new UnauthorizedException(INVALID_TOKEN);
        } catch (MalformedJwtException e) { // 올바르지 않은 토큰 형식
            throw new UnauthorizedException(INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(EXPIRED_TOKEN);
        } catch (IllegalArgumentException e) { // token 이 null 일 경우
            throw new UnauthorizedException(UNKNOWN_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new UnauthorizedException(UNSUPPORTED_TOKEN);
        } catch (SignatureException e) { // 서명을 확인하지 못하는 경우
            throw new UnauthorizedException(WRONG_SIGNATURE_TOKEN);
        }
    }

    public Long getUserIdFromJwtSubject(String token) {
        Claims claims = getBodyFromJwt(token);
        return claims.get(JwtConstants.USER_ID, Long.class);
    }

    private Claims getBodyFromJwt(String token) {
        JwtParser parser = getJwtParser();
        return parser.parseClaimsJws(token).getBody();
    }

    private JwtParser getJwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey).build();
    }

    public TokenReissueResponse getTokenReissueResponse(String refreshToken) {

        validateRefreshToken(refreshToken);
        Long userId = getUserIdFromJwtSubject(refreshToken);
        return TokenReissueResponse.of(issueAccessToken(userId),
                issueRefreshToken(userId));
    }

    public void validateTokenStartsWithBearer(String tokenWithBearer) {
        if (!tokenWithBearer.startsWith(Constants.BEARER)) {
            throw new UnauthorizedException(ErrorType.NOT_STARTS_WITH_BEARER);
        }
    }
}
