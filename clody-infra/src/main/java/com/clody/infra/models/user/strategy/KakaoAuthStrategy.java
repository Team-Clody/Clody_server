package com.clody.infra.models.user.strategy;

import com.clody.domain.user.Platform;
import com.clody.domain.user.dto.UserDomainInfo;
import com.clody.domain.user.repository.UserRepository;
import com.clody.domain.user.strategy.SocialRegisterStrategy;
import com.clody.domain.user.strategy.UserSocialInfo;
import com.clody.infra.external.feign.dto.response.kakao.KakaoTokenResponse;
import com.clody.infra.external.feign.dto.response.kakao.KakaoUserInfoResponse;
import com.clody.infra.external.feign.kakao.KakaoAuthClient;
import com.clody.infra.external.feign.kakao.KakaoUserInfoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoAuthStrategy implements SocialRegisterStrategy {

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
  public UserSocialInfo getUserInfo(UserDomainInfo info) {

    Platform platform = info.platform();

    //로컬 테스트시 아래의3줄 주석 해제
//        KakaoTokenResponse token = getKakaoToken(info.tokenWithBearer());
//        String accessTokenWithPrefix = KakaoTokenResponse.getTokenWithPrefix(token.access_token());
//        KakaoUserInfoResponse userInfo = getKakaoUserInfo(accessTokenWithPrefix);

    //로컬 테스트 아래의1줄 주석 하셈
    KakaoUserInfoResponse userInfo = getKakaoUserInfo(info.tokenWithBearer());
    return UserSocialInfo.of(userInfo.id(), platform, userInfo.kakaoAccount().email());
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
}
