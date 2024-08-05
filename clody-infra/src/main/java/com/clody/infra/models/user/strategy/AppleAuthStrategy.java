package com.clody.infra.models.user.strategy;

import static com.clody.infra.external.feign.apple.AppleLoginUtil.createClientSecret;

import com.clody.domain.user.Platform;
import com.clody.domain.user.User;
import com.clody.domain.user.dto.UserDomainInfo;
import com.clody.domain.user.repository.UserRepository;
import com.clody.domain.user.strategy.SocialRegisterStrategy;
import com.clody.domain.user.strategy.UserSocialInfo;
import com.clody.infra.external.feign.apple.AppleAuthClient;
import com.clody.infra.external.feign.apple.AppleIdentityTokenParser;
import com.clody.infra.external.feign.apple.ApplePublicKeyGenerator;
import com.clody.infra.external.feign.apple.ApplePublicKeysClient;
import com.clody.infra.external.feign.dto.response.apple.ApplePublicKeys;
import com.clody.infra.external.feign.dto.response.apple.AppleTokenResponse;
import com.clody.support.dto.type.ErrorType;
import com.clody.support.exception.BusinessException;
import com.clody.support.exception.NotFoundException;
import io.jsonwebtoken.Claims;
import java.security.PublicKey;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppleAuthStrategy implements SocialRegisterStrategy {

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
  public UserSocialInfo getUserInfo(UserDomainInfo info) {
    String userId = getApplePlatformId(info.idToken());
    validateDuplicateUser(userId);
    Platform platform = info.platform();
    AppleTokenResponse appleTokenResponse = getAppleToken(info.authTokenWithBearer());
    return UserSocialInfo.of(userId, platform);
  }

  public User signIn(UserDomainInfo info) {
    String userId = getApplePlatformId(info.idToken());
    validateDuplicateUser(userId);
    Platform platform = info.platform();
    AppleTokenResponse appleTokenResponse = getAppleToken(info.authTokenWithBearer());
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

    PublicKey publicKey = applePublicKeyGenerator.generatePublicKeyWithApplePublicKeys(headers,
        applePublicKeys);
    Claims claims = appleIdentityTokenParser.parseWithPublicKeyAndGetClaims(identityToken,
        publicKey);

    return claims.getSubject();
  }

}
