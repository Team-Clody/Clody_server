package com.donkeys_today.server.docs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.donkeys_today.server.domain.refresh.RefreshToken;
import com.donkeys_today.server.domain.refresh.RefreshTokenRepository;
import com.donkeys_today.server.support.exception.UnauthorizedException;
import com.donkeys_today.server.support.jwt.JWTConstants;
import com.donkeys_today.server.support.jwt.JWTUtil;
import com.donkeys_today.server.support.jwt.JwtTokenProvider;
import com.donkeys_today.server.support.jwt.JwtValidationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JwtUtilsTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;


    @Autowired
    private JWTUtil jwtUtil;


    @Test
    @DisplayName("JWT에서 사용자 ID 가져오기 테스트")
    public void testGetUserFromJwt() {
        String token = jwtTokenProvider.issueAccessToken(1L, "USER");
        String userId = jwtUtil.getUserFromJwt(token);
        assertEquals("1", userId);
    }

    @Test
    @DisplayName("JWT에서 역할 가져오기 테스트")
    public void testGetRoleFromJwt() {
        String token = jwtTokenProvider.issueAccessToken(1L, "USER");
        String role = jwtUtil.getRoleFromJwt(token);
        assertEquals("USER", role);
    }

    @Test
    @DisplayName("JWT에서 유형 가져오기 테스트")
    public void testGetTypeFromJwt() {
        String token = jwtTokenProvider.issueAccessToken(1L, "USER");
        String type = jwtUtil.getTypeFromJwt(token);
        assertEquals(JWTConstants.ACCESS_TOKEN, type);
    }

    @Test
    @DisplayName("토큰 유효성 검사 - 유효한 액세스 토큰 테스트")
    public void testValidateToken_ValidAccessToken() {
        String token = jwtTokenProvider.issueAccessToken(1L, "USER");
        JwtValidationType validationType = jwtUtil.validateToken(token);
        assertEquals(JwtValidationType.VALID_ACCESS, validationType);
    }

    @Test
    @DisplayName("토큰 유효성 검사 - 잘못된 토큰 테스트")
    public void testValidateToken_InvalidToken() {
        assertThrows(UnauthorizedException.class, () -> jwtUtil.validateToken("invalidtoken"));
    }

    @Test
    @DisplayName("리프레시 토큰 유효성 검사 - 유효한 토큰")
    public void testValidateRefreshToken_Valid() {
        String refreshToken = jwtTokenProvider.issueRefreshToken(2L, "ADMIN");
        // 유효성 검사 메서드 호출. 예외가 발생하지 않으면 성공.
        jwtUtil.validateRefreshToken(refreshToken);
    }


    @Test
    @DisplayName("리프레시 저장 확인 테스트")
    public void saveAndGetRefreshToken() {
        String token = jwtTokenProvider.issueRefreshToken(2L, "ADMIN");
        RefreshToken findRefreshToken = refreshTokenRepository.findByRefresh(token);

        // 유효성 검사 메서드 호출. 예외가 발생하지 않으면 성공.
        jwtUtil.validateRefreshToken(findRefreshToken.getRefresh());

        assertEquals("2", jwtUtil.getUserFromJwt(findRefreshToken.getRefresh()));
        assertEquals("ADMIN", jwtUtil.getRoleFromJwt(findRefreshToken.getRefresh()));
        assertEquals(JWTConstants.REFRESH_TOKEN, jwtUtil.getTypeFromJwt(findRefreshToken.getRefresh()));

    }

    @Test
    @DisplayName("리프레시 토큰 삭제 테스트")
    public void deleteRefreshToken() {
        String token = jwtTokenProvider.issueRefreshToken(3L, "ADMIN");
        jwtUtil.validateRefreshToken(token);
        refreshTokenRepository.deleteByRefresh(token);

    }
}