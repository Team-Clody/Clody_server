package com.donkeys_today.server.application.user.sterategy;

import static com.donkeys_today.server.support.dto.type.ErrorType.INVALID_TOKEN;
import static com.donkeys_today.server.support.feign.apple.AppleLoginUtil.createClientSecret;

import com.donkeys_today.server.domain.user.Platform;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.domain.user.UserRepository;
import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.BusinessException;
import com.donkeys_today.server.support.exception.NotFoundException;
import com.donkeys_today.server.support.exception.UnauthorizedException;
import com.donkeys_today.server.support.feign.apple.AppleAuthClient;
import com.donkeys_today.server.support.feign.dto.response.apple.AppleTokenResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.text.ParseException;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppleAuthStrategy implements SocialRegisterSterategy {

    private final AppleAuthClient appleAuthClient;
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
        try {
            AppleTokenResponse token = getAppleToken(authToken);
            SignedJWT signedJWT = SignedJWT.parse(token.id_token());
            ReadOnlyJWTClaimsSet getPayload = signedJWT.getJWTClaimsSet();
            ObjectMapper objectMapper = new ObjectMapper();
            JSONObject payload = objectMapper.readValue(getPayload.toJSONObject().toJSONString(), JSONObject.class);

            String userId = String.valueOf(payload.get("sub"));
            String email = String.valueOf(payload.get("email"));
            validateDuplicateUser(userId);
            return User.builder()
                    .platformID(userId)
                    .platform(platform)
                    .nickName("nickname")
                    .email(email)
                    .build();
        } catch (JsonProcessingException | ParseException e) {
            throw new UnauthorizedException(INVALID_TOKEN);
        }

    }

    @Override
    public User signIn(Platform platform, String authToken) {
        try {
            AppleTokenResponse token = getAppleToken(authToken);
            SignedJWT signedJWT = SignedJWT.parse(token.id_token());
            ReadOnlyJWTClaimsSet getPayload = signedJWT.getJWTClaimsSet();
            ObjectMapper objectMapper = new ObjectMapper();
            JSONObject payload = objectMapper.readValue(getPayload.toJSONObject().toJSONString(), JSONObject.class);

            String userId = String.valueOf(payload.get("sub"));
            String email = String.valueOf(payload.get("email"));

            return findByPlatformAndPlatformId(platform, userId);
        } catch (ParseException | JsonProcessingException e) {
            throw new UnauthorizedException(INVALID_TOKEN);
        }

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
}
