package com.clody.infra.models.user.strategy;

import com.clody.domain.user.Platform;
import com.clody.domain.user.User;
import com.clody.domain.user.dto.UserDomainInfo;
import com.clody.domain.user.repository.UserRepository;
import com.clody.domain.user.strategy.SocialRegisterStrategy;
import com.clody.domain.user.strategy.UserSocialInfo;
import com.clody.infra.external.feign.dto.response.google.GoogleTokenResponse;
import com.clody.infra.external.feign.dto.response.google.GoogleUserInfoResponse;
import com.clody.infra.external.feign.dto.response.kakao.KakaoTokenResponse;
import com.clody.infra.external.feign.google.GoogleAuthClient;
import com.clody.infra.external.feign.google.GoogleUserInfoClient;
import com.clody.support.dto.type.ErrorType;
import com.clody.support.exception.BusinessException;
import com.clody.support.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleAuthStrategy implements SocialRegisterStrategy {

    private final GoogleAuthClient googleAuthClient;
    private final GoogleUserInfoClient googleUserInfoClient;
    private final UserRepository userRepository;

    @Value("${google.client-id}")
    private String googleClientId;
    @Value("${google.client-secret}")
    private String googleClientSecret;
    @Value("${google.redirect-uri}")
    private String redirect_uri;

    public UserSocialInfo getUserInfo(UserDomainInfo info) {

        Platform platform = info.platform();
        GoogleTokenResponse token = getGoogleToken(info.authTokenWithBearer());
        String accessTokenWithPrefix = GoogleTokenResponse.getTokenWithPrefix(token.access_token());
        GoogleUserInfoResponse userInfo = getKakaoUserInfo(accessTokenWithPrefix);
        return UserSocialInfo.of(userInfo.sub(), platform);
    }

    public User signIn(UserDomainInfo info) {
        Platform platform = info.platform();
        GoogleTokenResponse token = getGoogleToken(info.authTokenWithBearer());
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
