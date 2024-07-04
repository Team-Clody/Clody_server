package com.donkeys_today.server.application.auth;

import static com.donkeys_today.server.support.dto.type.ErrorType.EXPIRED_TOKEN;
import static com.donkeys_today.server.support.dto.type.ErrorType.INVALID_REFRESH_TOKEN;
import static com.donkeys_today.server.support.dto.type.ErrorType.INVALID_TOKEN;
import static com.donkeys_today.server.support.dto.type.ErrorType.MISMATCH_REFRESH_TOKEN;
import static com.donkeys_today.server.support.dto.type.ErrorType.UNKNOWN_TOKEN;
import static com.donkeys_today.server.support.dto.type.ErrorType.UNSUPPORTED_TOKEN;
import static com.donkeys_today.server.support.dto.type.ErrorType.WRONG_SIGNATURE_TOKEN;
import static com.donkeys_today.server.support.jwt.JwtConstants.REFRESH_TOKEN_PREFIX;

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
    this.refreshTokenRepository  = refreshTokenRepository;
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
    String key = REFRESH_TOKEN_PREFIX + userId;
    refreshTokenRepository.saveRefreshToken(userId,refreshToken, JwtConstants.REFRESH_TOKEN_EXPIRATION_TIME);
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
  }

  @Override
  public void equalsRefreshToken(String refreshToken, String savedRefreshToken) {
    if(!refreshToken.equals(savedRefreshToken)) {
      throw new UnauthorizedException(MISMATCH_REFRESH_TOKEN);
    }
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

  public Long getUserIdFromJwtSubject(String token) {
    Claims claims = getBodyFromJwt(token);
    return claims.get(JwtConstants.USER_ID, Long.class);
  }

  private Claims getBodyFromJwt(String token) {
    JwtParser parser = getJwtParser();
    return parser.parseClaimsJws(token).getBody();
  }

  private JwtParser getJwtParser(){
    return Jwts.parserBuilder()
        .setSigningKey(secretKey).build();
  }
}
