package com.clody.infra.models.user.strategy;


import com.clody.domain.user.Platform;
import com.clody.domain.user.dto.UserDomainInfo;
import com.clody.domain.user.strategy.SocialRegisterStrategy;
import com.clody.domain.user.strategy.UserSocialInfo;
import com.clody.infra.external.feign.apple.AppleAuthClient;
import com.clody.infra.external.feign.apple.AppleIdentityTokenParser;
import com.clody.infra.external.feign.apple.AppleIdentityTokenValidator;
import com.clody.infra.external.feign.apple.ApplePublicKeyGenerator;
import com.clody.infra.external.feign.dto.response.apple.ApplePublicKeys;
import com.clody.support.dto.type.ErrorType;
import com.clody.support.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import java.security.PublicKey;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppleAuthStrategy implements SocialRegisterStrategy {

  private final AppleAuthClient appleAuthClient;
  private final AppleIdentityTokenParser appleIdentityTokenParser;
  private final ApplePublicKeyGenerator applePublicKeyGenerator;
  private final AppleIdentityTokenValidator appleIdentityTokenValidator;

  @Override
  public UserSocialInfo getUserInfo(UserDomainInfo info) {

    Platform platform = info.platform();
    String platformId = getApplePlatformId(info.idToken());
    return UserSocialInfo.of(platformId, platform);
  }

  public String getApplePlatformId(String identityToken) {
    Map<String, String> headers = appleIdentityTokenParser.parseHeaders(identityToken);
    ApplePublicKeys applePublicKeys = appleAuthClient.getApplePublicKeys();
    PublicKey publicKey = applePublicKeyGenerator.generatePublicKeyWithApplePublicKeys(headers, applePublicKeys);
    Claims claims = appleIdentityTokenParser.parseWithPublicKeyAndGetClaims(identityToken, publicKey);
    validateClaims(claims);

    return claims.getSubject();
  }
  private void validateClaims(Claims claims) {
    if (!appleIdentityTokenValidator.isValidAppleIdentityToken(claims)) {
      throw new UnauthorizedException(ErrorType.INVALID_ID_TOKEN);
    }
  }
}
