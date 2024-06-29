package com.donkeys_today.server.docs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.donkeys_today.server.support.exception.UnauthorizedException;
import com.donkeys_today.server.support.jwt.JWTConstants;
import com.donkeys_today.server.support.jwt.JWTUtil;
import com.donkeys_today.server.support.jwt.JwtTokenProvider;
import com.donkeys_today.server.support.jwt.JwtValidationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private JWTUtil jwtUtil;

    @Test
    @DisplayName("액세스 토큰 발급 테스트")
    public void testIssueAccessToken() {
        String accessToken = jwtTokenProvider.issueAccessToken(1L, "USER");
        assertNotNull(accessToken);
    }

    @Test
    @DisplayName("리프레시 토큰 발급 테스트")
    public void testIssueRefreshToken() {
        String refreshToken = jwtTokenProvider.issueRefreshToken(1L, "USER");
        assertNotNull(refreshToken);
    }


    @Test
    @DisplayName("토큰 생성 테스트")
    public void testGenerateToken() {
        String token = jwtTokenProvider.generateToken(JWTConstants.ACCESS_TOKEN, 1L, "USER", 60000L);
        assertNotNull(token);
    }

    @Test
    @DisplayName("서명 키 가져오기 테스트")
    public void testGetSigningKey() {
        assertNotNull(jwtTokenProvider.getSigningKey());
    }


}