package com.donkeys_today.server.application.user.sterategy;

import com.donkeys_today.server.domain.user.Platform;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.domain.user.UserRepository;
import com.donkeys_today.server.presentation.auth.dto.request.UserSignInRequest;
import com.donkeys_today.server.presentation.auth.dto.request.UserSignUpRequest;
import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.BusinessException;
import com.donkeys_today.server.support.exception.NotFoundException;
import com.donkeys_today.server.support.feign.dto.response.google.GoogleTokenResponse;
import com.donkeys_today.server.support.feign.dto.response.google.GoogleUserInfoResponse;
import com.donkeys_today.server.support.feign.dto.response.kakao.KakaoTokenResponse;
import com.donkeys_today.server.support.feign.google.GoogleAuthClient;
import com.donkeys_today.server.support.feign.google.GoogleUserInfoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleAuthStrategy implements SocialRegisterSterategy {

    private final GoogleAuthClient googleAuthClient;
    private final GoogleUserInfoClient googleUserInfoClient;
    private final UserRepository userRepository;

    @Value("${google.client-id}")
    private String googleClientId;
    @Value("${google.client-secret}")
    private String googleClientSecret;
    @Value("${google.redirect-uri}")
    private String redirect_uri;

    @Override
    public User signUp(UserSignUpRequest request, String authToken) {

        Platform platform = getPlatformFromRequestString(request.platform());
        GoogleTokenResponse token = getGoogleToken(authToken);
        String accessTokenWithPrefix = GoogleTokenResponse.getTokenWithPrefix(token.access_token());
        GoogleUserInfoResponse userInfo = getKakaoUserInfo(accessTokenWithPrefix);
        validateDuplicateUser(userInfo);
        return User.builder()
                .platformID(userInfo.sub())
                .platform(platform)
                .nickName(request.name())
                .email(userInfo.email())
                .build();
    }

    @Override
    public User signIn(UserSignInRequest request, String authToken) {
        Platform platform = getPlatformFromRequestString(request.platform());
        GoogleTokenResponse token = getGoogleToken(authToken);
        String accessTokenWithPrefix = KakaoTokenResponse.getTokenWithPrefix(token.access_token());
        GoogleUserInfoResponse userInfo = getKakaoUserInfo(accessTokenWithPrefix);
        return findByPlatformAndPlatformId(platform, userInfo.sub());
    }

    private User findByPlatformAndPlatformId(Platform platform, String platformId) {
        return userRepository.findByPlatformAndPlatformID(platform, platformId).orElseThrow(
                () -> new NotFoundException(ErrorType.NOTFOUND_USER_ERROR)
        );
    }

    private GoogleTokenResponse getGoogleToken(String authToken) {
        return googleAuthClient.getOauth2AccessToken(
                authToken,
                googleClientId,
                googleClientSecret,
                redirect_uri,
                "authorization_code"
        );
    }

    private GoogleUserInfoResponse getKakaoUserInfo(String accessToken) {
        return googleUserInfoClient.getUserInformation(accessToken);
    }

    private void validateDuplicateUser(GoogleUserInfoResponse userInfo) {
        if (userRepository.existsByPlatformAndPlatformID(Platform.GOOGLE, userInfo.sub())) {
            throw new BusinessException(ErrorType.DUPLICATED_USER_ERROR);
        }
    }

    private Platform getPlatformFromRequestString(String request) {
        return Platform.fromString(request);
    }
}
