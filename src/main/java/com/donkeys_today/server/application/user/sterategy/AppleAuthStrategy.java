package com.donkeys_today.server.application.user.sterategy;

import static com.donkeys_today.server.support.feign.apple.AppleLoginUtil.createClientSecret;

import com.donkeys_today.server.domain.user.Platform;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.infrastructure.user.UserRepository;
import com.donkeys_today.server.presentation.user.dto.requset.UserSignInRequest;
import com.donkeys_today.server.presentation.user.dto.requset.UserSignUpRequest;
import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.BusinessException;
import com.donkeys_today.server.support.exception.NotFoundException;
import com.donkeys_today.server.support.feign.apple.AppleAuthClient;
import com.donkeys_today.server.support.feign.apple.AppleIdentityTokenParser;
import com.donkeys_today.server.support.feign.apple.ApplePublicKeyGenerator;
import com.donkeys_today.server.support.feign.apple.ApplePublicKeysClient;
import com.donkeys_today.server.support.feign.dto.response.apple.ApplePublicKeys;
import com.donkeys_today.server.support.feign.dto.response.apple.AppleTokenResponse;
import io.jsonwebtoken.Claims;
import java.security.PublicKey;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppleAuthStrategy implements SocialRegisterSterategy {

    private final AppleAuthClient appleAuthClient;
    private final UserRepository userRepository;
    private final AppleIdentityTokenParser appleIdentityTokenParser;
    private final ApplePublicKeysClient applePublicKeysClient;
    private final ApplePublicKeyGenerator applePublicKeyGenerator;

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
    public User signUp(UserSignUpRequest userSignUpRequest, String authToken) {
        String userId = getApplePlatformId(userSignUpRequest.id_token());
        validateDuplicateUser(userId);
        Platform platform = getPlatformFromRequestString(userSignUpRequest.platform());
        AppleTokenResponse appleTokenResponse = getAppleToken(authToken);

        return User.builder()
                .platformID(userId)
                .platform(platform)
                .nickName(userSignUpRequest.name())
                .email(userSignUpRequest.email())
                .build();

    }

    @Override
    public User signIn(UserSignInRequest userSignInRequest, String authToken) {

        String userId = getApplePlatformId(userSignInRequest.id_token());
        validateDuplicateUser(userId);
        Platform platform = getPlatformFromRequestString(userSignInRequest.platform());
        AppleTokenResponse appleTokenResponse = getAppleToken(authToken);

        return findByPlatformAndPlatformId(platform, userId);

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

    private void validateDuplicateUser(String userId) {
        if (userRepository.existsByPlatformAndPlatformID(Platform.APPLE, userId)) {
            throw new BusinessException(ErrorType.DUPLICATED_USER_ERROR);
        }
    }

    private Platform getPlatformFromRequestString(String request) {
        return Platform.fromString(request);
    }

    public String getApplePlatformId(String identityToken) {
        Map<String, String> headers = appleIdentityTokenParser.parseHeaders(identityToken);
        ApplePublicKeys applePublicKeys = applePublicKeysClient.getApplePublicKeys();

        PublicKey publicKey = applePublicKeyGenerator.generatePublicKeyWithApplePublicKeys(headers, applePublicKeys);
        Claims claims = appleIdentityTokenParser.parseWithPublicKeyAndGetClaims(identityToken, publicKey);

        return claims.getSubject();
    }

}
