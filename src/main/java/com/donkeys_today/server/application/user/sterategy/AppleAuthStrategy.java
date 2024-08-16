package com.donkeys_today.server.application.user.sterategy;

import com.donkeys_today.server.domain.user.Platform;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.infrastructure.user.UserRepository;
import com.donkeys_today.server.presentation.auth.dto.request.UserSignInRequest;
import com.donkeys_today.server.presentation.auth.dto.request.UserSignUpRequest;
import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.BusinessException;
import com.donkeys_today.server.support.exception.NotFoundException;
import com.donkeys_today.server.support.exception.UnauthorizedException;
import com.donkeys_today.server.support.feign.apple.AppleFeignClient;
import com.donkeys_today.server.support.feign.apple.AppleIdentityTokenParser;
import com.donkeys_today.server.support.feign.apple.AppleIdentityTokenValidator;
import com.donkeys_today.server.support.feign.apple.ApplePublicKeyGenerator;
import com.donkeys_today.server.support.feign.dto.response.apple.ApplePublicKeys;

import io.jsonwebtoken.Claims;

import java.security.PublicKey;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppleAuthStrategy implements SocialRegisterSterategy {

  private final UserRepository userRepository;
  private final AppleIdentityTokenParser appleIdentityTokenParser;
  private final ApplePublicKeyGenerator applePublicKeyGenerator;
  private final AppleFeignClient appleFeignClient;
  private final AppleIdentityTokenValidator appleIdentityTokenValidator;


  @Override
  public User signUp(UserSignUpRequest userSignUpRequest, String idTokenWithBearer) {

    String id_token = idTokenWithBearer.split(" ")[1];
    String platformId = getApplePlatformId(id_token);
    validateDuplicateUser(platformId);
    Platform platform = getPlatformFromRequestString(userSignUpRequest.platform());
    return User.builder()
            .platformID(platformId)
            .platform(platform)
            .nickName(userSignUpRequest.name())
            .email(userSignUpRequest.email())
            .build();

  }

  @Override
  public User signIn(UserSignInRequest userSignInRequest, String idTokenWithBearer) {

    String id_token = idTokenWithBearer.split(" ")[1];
    String platformId = getApplePlatformId(id_token);
    Platform platform = getPlatformFromRequestString(userSignInRequest.platform());

    return findByPlatformAndPlatformId(platform, platformId);
  }

  private User findByPlatformAndPlatformId(Platform platform, String platformId) {
    return userRepository.findByPlatformAndPlatformID(platform, platformId).orElseThrow(
        () -> new NotFoundException(ErrorType.DUPLICATED_USER_ERROR)
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
    ApplePublicKeys applePublicKeys = appleFeignClient.getApplePublicKeys();
    PublicKey publicKey = applePublicKeyGenerator.generatePublicKeyWithApplePublicKeys(headers, applePublicKeys);
    Claims claims = appleIdentityTokenParser.parseWithPublicKeyAndGetClaims(identityToken, publicKey);
    System.out.println(claims.toString());
    validateClaims(claims);

    return claims.getSubject();
  }

  private void validateClaims(Claims claims) {
    if (!appleIdentityTokenValidator.isValidAppleIdentityToken(claims)) {
      throw new UnauthorizedException(ErrorType.INVALID_ID_TOKEN);
    }
  }

}
