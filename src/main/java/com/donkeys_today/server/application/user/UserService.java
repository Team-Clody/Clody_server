package com.donkeys_today.server.application.user;

import com.donkeys_today.server.domain.user.Platform;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.domain.user.UserRepository;
import com.donkeys_today.server.presentation.user.dto.requset.UserSignInRequest;
import com.donkeys_today.server.presentation.user.dto.requset.UserSignUpRequest;
import com.donkeys_today.server.presentation.user.dto.response.UserSignInResponse;
import com.donkeys_today.server.presentation.user.dto.response.UserSignUpResponse;
import com.donkeys_today.server.support.jwt.JwtProvider;
import com.donkeys_today.server.support.jwt.RefreshTokenRepository;
import com.donkeys_today.server.support.jwt.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRetriever userRetriever;
    private final UserAuthenticator userAuthenticator;
    private final UserUpdater userUpdater;
    private final UserRemover userRemover;

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public UserSignUpResponse signUp(final String authorizationCode,
                                     final UserSignUpRequest request) {
        //플랫폼에서 플랫폼 추출해야 함
        //그래서 해당 플랫폼에다가 유저 생성 기능 위임
        Platform platform = getPlatformFromRequestString(request.platform());
        User newUser = userAuthenticator.signUp(authorizationCode, platform);
        User savedUser = userRepository.save(newUser);
        //알람 설정했을 경우, 알람 설정 (이벤트 분리)
//    userAuthenticator.setUserAlarm(savedUser,request.alarmAgreement(),request.alarmTime());
        //토큰 생성하고 redis에 저장.
        Token token = userAuthenticator.issueToken(savedUser.getId());
        return UserSignUpResponse.of(savedUser.getId(), token.accessToken(), token.refreshToken());
    }

    public UserSignInResponse signIn(final String authorizationCode,
                                     final UserSignInRequest request) {

        Platform platform = getPlatformFromRequestString(request.platform());
        User foundUser = userAuthenticator.signIn(authorizationCode, platform);
        Token token = userAuthenticator.issueToken(foundUser.getId());
        return UserSignInResponse.of(foundUser.getId(), token.accessToken(), token.refreshToken());
    }

    private Platform getPlatformFromRequestString(String request) {
        return Platform.fromString(request);
    }
}
