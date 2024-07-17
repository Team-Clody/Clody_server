package com.donkeys_today.server.application.user.sterategy;

import com.donkeys_today.server.domain.user.Platform;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.infrastructure.user.UserRepository;
import com.donkeys_today.server.presentation.auth.dto.request.UserSignInRequest;
import com.donkeys_today.server.presentation.auth.dto.request.UserSignUpRequest;
import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.auth.SignInException;
import com.donkeys_today.server.support.exception.auth.SignUpException;
import com.donkeys_today.server.support.feign.dto.response.kakao.KakaoTokenResponse;
import com.donkeys_today.server.support.feign.dto.response.kakao.KakaoUserInfoResponse;
import com.donkeys_today.server.support.feign.kakao.KakaoAuthClient;
import com.donkeys_today.server.support.feign.kakao.KakaoUserInfoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoAuthStrategy implements SocialRegisterSterategy {

    private final KakaoAuthClient kakaoAuthClient;
    private final KakaoUserInfoClient kakaoUserInfoClient;
    private final UserRepository userRepository;

    @Value("${kakao.client-id}")
    private String kakaoClientId;
    @Value("${kakao.client-secret}")
    private String kakaoClientSecret;
    @Value("${kakao.redirect-uri}")
    private String redirect_uri;

    @Override
    public User signUp(UserSignUpRequest request, String accessTokenWithBearer) {

        Platform platform = getPlatformFromRequestString(request.platform());

        //로컬 테스트시 아래의3줄 주석 해제
//        KakaoTokenResponse token = getKakaoToken(accessTokenWithBearer);
//        String accessTokenWithPrefix = KakaoTokenResponse.getTokenWithPrefix(token.access_token());
//        KakaoUserInfoResponse userInfo = getKakaoUserInfo(accessTokenWithPrefix);

        //로컬 테스트 아래의1줄 주석 하셈
        KakaoUserInfoResponse userInfo = getKakaoUserInfo(accessTokenWithBearer);

        validateDuplicateUser(userInfo);
        return User.builder()
                .platformID(userInfo.id())
                .platform(platform)
                .nickName(request.name())
                .email(userInfo.kakaoAccount().email())
                .build();
    }

    @Override
    public User signIn(UserSignInRequest userSignInRequest, String accessTokenWithBearer) {

        Platform platform = getPlatformFromRequestString(userSignInRequest.platform());

        //로컬 테스트시 아래의3줄 주석 해제
//        KakaoTokenResponse token = getKakaoToken(accessTokenWithBearer);
//        String accessTokenWithPrefix = KakaoTokenResponse.getTokenWithPrefix(token.access_token());
//        KakaoUserInfoResponse userInfo = getKakaoUserInfo(accessTokenWithPrefix);

        //로컬 테스트시 아래의 1줄 주석 하셈
        KakaoUserInfoResponse userInfo = getKakaoUserInfo(accessTokenWithBearer);

        return findByPlatformAndPlatformId(platform, userInfo.id());
    }

    private User findByPlatformAndPlatformId(Platform platform, String platformId) {
        return userRepository.findByPlatformAndPlatformID(platform, platformId).orElseThrow(
                () -> new SignInException(ErrorType.USER_NOT_FOUND)
        );
    }

    private KakaoTokenResponse getKakaoToken(String authToken) {
        return kakaoAuthClient.getOauth2AccessToken(
                "authorization_code",
                kakaoClientId,
                kakaoClientSecret,
                redirect_uri,
                authToken
        );
    }

    private KakaoUserInfoResponse getKakaoUserInfo(String accessToken) {
        return kakaoUserInfoClient.getUserInformation(accessToken);
    }

    private void validateDuplicateUser(KakaoUserInfoResponse userInfo) {
        if (userRepository.existsByPlatformAndPlatformID(Platform.KAKAO, userInfo.id())) {
            throw new SignUpException(ErrorType.DUPLICATED_USER_ERROR);
        }
    }

    private Platform getPlatformFromRequestString(String request) {
        return Platform.fromString(request);
    }
}
