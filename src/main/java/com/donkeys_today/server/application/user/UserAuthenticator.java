package com.donkeys_today.server.application.user;

import static com.donkeys_today.server.support.jwt.JwtConstants.REFRESH_TOKEN_EXPIRATION_TIME;

import com.donkeys_today.server.application.user.sterategy.AppleAuthStrategy;
import com.donkeys_today.server.application.user.sterategy.GoogleAuthStrategy;
import com.donkeys_today.server.application.user.sterategy.KakaoAuthStrategy;
import com.donkeys_today.server.application.user.sterategy.SocialRegisterSterategy;
import com.donkeys_today.server.domain.user.Platform;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.presentation.user.dto.requset.UserSignInRequest;
import com.donkeys_today.server.presentation.user.dto.requset.UserSignUpRequest;
import com.donkeys_today.server.support.jwt.JwtProvider;
import com.donkeys_today.server.support.jwt.RefreshTokenRepository;
import com.donkeys_today.server.support.jwt.Token;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAuthenticator {

    private final Map<Platform, SocialRegisterSterategy> provider = new HashMap<>();

    private final KakaoAuthStrategy kakaoAuthStrategy;
    private final GoogleAuthStrategy googleAuthStrategy;
    private final AppleAuthStrategy appleAuthStrategy;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostConstruct
    public void initSocialLoginProvider() {
        provider.put(Platform.KAKAO, kakaoAuthStrategy);
        provider.put(Platform.GOOGLE, googleAuthStrategy);
        provider.put(Platform.APPLE, appleAuthStrategy);
    }

    public User signUp(String authToken, UserSignUpRequest request) {
        Platform platform = getPlatformFromRequestString(request.platform());
        return provider.get(platform).signUp(request, authToken);
    }

    public User signIn(String authToken, UserSignInRequest request) {
        Platform platform = getPlatformFromRequestString(request.platform());
        return provider.get(platform).signIn(request, authToken);
    }

    public void setUserAlarm(User user, boolean agreement, LocalTime localTime) {
        if (agreement) {
            user.updateUserAlarmAgreement(true);
            //TODO 해당 부분은 와이어프레임에 따라서, 온보딩 과정에서 알람을 설정할지, 아니면 따로 설정할 지 정해지는 것 보고 구현
        }
    }

    public Token issueToken(Long id) {
        String accessToken = jwtProvider.issueAccessToken(id);
        String refreshToken = jwtProvider.issueRefreshToken(id);
        storeRefreshToken(id, refreshToken);
        return Token.of(accessToken, refreshToken);
    }

    private void storeRefreshToken(Long id, String refreshToken) {
        refreshTokenRepository.saveRefreshToken(id, refreshToken, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    private Platform getPlatformFromRequestString(String platform) {
        return Platform.fromString(platform);
    }
}
