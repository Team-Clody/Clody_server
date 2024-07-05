package com.donkeys_today.server.application.user.sterategy;

import static com.donkeys_today.server.support.feign.apple.AppleLoginUtil.createClientSecret;

import com.donkeys_today.server.domain.user.Platform;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.domain.user.UserRepository;
import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.BusinessException;
import com.donkeys_today.server.support.exception.NotFoundException;
import com.donkeys_today.server.support.feign.apple.AppleAuthClient;
import com.donkeys_today.server.support.feign.dto.response.apple.AppleTokenResponse;
import com.donkeys_today.server.support.feign.dto.response.apple.AppleUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppleAuthStrategy implements SocialRegisterSterategy {

    private final AppleAuthClient appleAuthClient;
    //    private final AppleUserInfoClient AppleUserInfoClient;
    private final UserRepository userRepository;

    @Value("${apple.client-id}")
    private String appleClientId;
    @Value("${apple.redirect-uri}")
    private String redirect_uri;
    @Value("${apple.team-id}")
    private String appleTeamtId;
    @Value("${apple.key-id}")
    private String appleKeyId;
    @Value("${apple.key-path}")
    private String appleKeyPath;


    @Override
    public User signUp(Platform platform, String authToken) {
        AppleTokenResponse token = getAppleToken(authToken);


        token 안에 있는 id_token 을 검증 해야함!!!!!


        validateDuplicateUser(userInfo);
        return User.builder()
                .platformID(userInfo.sub())
                .platform(platform)
                .nickName(userInfo.name())
                .email(userInfo.email())
                .build();
    }

    @Override
    public User signIn(Platform platform, String authToken) {
        AppleTokenResponse token = getAppleToken(authToken);

        token.id_token 검증 해야함 .

        return findByPlatformAndPlatformId(platform, userInfo.sub());
    }

    private User findByPlatformAndPlatformId(Platform platform, String platformId) {
        return userRepository.findByPlatformAndPlatformID(platform, platformId).orElseThrow(
                () -> new NotFoundException(ErrorType.DUPLICATED_USER_ERROR)
        );
    }

    private AppleTokenResponse getAppleToken(String authToken) {
        return appleAuthClient.getOauth2AccessToken(
                "authorization_code",
                appleClientId,
                redirect_uri,
                authToken,
                createClientSecret(
                        appleTeamtId, appleClientId, appleKeyId, appleKeyPath, redirect_uri
                )

        );
    }

    private void validateDuplicateUser(AppleUserInfoResponse userInfo) {
        if (userRepository.existsByPlatformAndPlatformID(Platform.GOOGLE, userInfo.sub())) {
            throw new BusinessException(ErrorType.DUPLICATED_USER_ERROR);
        }
    }
}
