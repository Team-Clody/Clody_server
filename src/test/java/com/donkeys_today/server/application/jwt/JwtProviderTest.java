package com.donkeys_today.server.application.jwt;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.donkeys_today.server.application.auth.JwtProviderImpl;
import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.UnauthorizedException;
import com.donkeys_today.server.support.jwt.JwtConstants;
import com.donkeys_today.server.support.jwt.JwtProvider;
import com.donkeys_today.server.support.jwt.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

public class JwtProviderTest {

  @InjectMocks
  private JwtProvider jwtProvider;

  private String secret = "시크릿시크릿시크릿시크릿시크릿시크릿시크릿시크릿시크릿시크릿";
  private SecretKey secretKey;

  @Mock
  private RefreshTokenRepository refreshTokenRepository;

  @BeforeEach
  public void setUp() {
    secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    jwtProvider = new JwtProviderImpl(secret,refreshTokenRepository);
    ReflectionTestUtils.setField(jwtProvider, "JWT_SECRET", secret);
  }

  @Test
  @DisplayName("액세스 토큰 생성 검증")
  public void 올바른_토큰_생성_확인(){
    //given
    long userId = 1;

    //when
    String token = jwtProvider.issueAccessToken(userId);

    //then
    jwtProvider.validateAccessToken(token);
  }

  @Test
  @DisplayName("User ID 추출 테스트")
  public void 유저_ID_반환_테스트(){

    //given
    long userId = 1;
    String token = jwtProvider.issueAccessToken(userId);

    //when
    Long foundUserId = jwtProvider.getUserIdFromJwtSubject(token);

    //then
    then(foundUserId).isEqualTo(userId);
  }

  @Test
  @DisplayName("올바르지 않은 AccessToken일 경우 예외 반환")
  public void 엑세스토큰_예외_반환(){
    //given
    var 올바르지_않은_토큰 = "malformedToken";

    //when
    UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
      jwtProvider.validateAccessToken(올바르지_않은_토큰);
    });

    //then
    assertThat(exception.getErrorType()).isEqualTo(ErrorType.INVALID_TOKEN);
  }

  @Test
  @DisplayName("올바르지 않은 서명일 경우 예외 반환")
  public void 올바르지_않은_서명(){
    //given
    var 올바르지_않은_서명 = Jwts.builder()
        .setSubject("user")
        .claim("미지원","미지원")
        .signWith(Keys.hmacShaKeyFor("다른키다른키다른키다른키다른키다른키다른키다른키다른키다른키다른키다른키다른키".getBytes()), SignatureAlgorithm.HS256)
        .compact();

    //when
    UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
      jwtProvider.validateAccessToken(올바르지_않은_서명);
    });

    //then
    assertThat(exception.getErrorType()).isEqualTo(ErrorType.WRONG_SIGNATURE_TOKEN);
  }

  @Test
  @DisplayName("만료된 토큰일 경우 예외 반환")
  public void 만료된_토큰(){
    //given
    final Claims claims = Jwts.claims()
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis()-1000));      // 만료 시간

    claims.put(JwtConstants.TOKEN_TYPE, JwtConstants.ACCESS_TOKEN);
    claims.put(JwtConstants.USER_ID, 1);
    var 만료된_토큰 = Jwts.builder()
        .setSubject("user")
        .setClaims(claims)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis()-1000))
        .signWith(secretKey)
        .compact();

    //when
    UnauthorizedException exception = assertThrows(UnauthorizedException.class, ()->{
      jwtProvider.validateAccessToken(만료된_토큰);
    });

    //then
    assertThat(exception.getErrorType()).isEqualTo(ErrorType.EXPIRED_TOKEN);
  }
}
