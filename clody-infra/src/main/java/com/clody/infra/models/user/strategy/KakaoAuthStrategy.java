package com.clody.infra.models.user.strategy;

import com.clody.domain.user.Platform;
import com.clody.domain.user.User;
import com.clody.domain.user.dto.UserDomainInfo;
import com.clody.domain.user.repository.UserRepository;
import com.clody.domain.user.strategy.SocialRegisterStrategy;
import com.clody.domain.user.strategy.UserSocialInfo;
import com.clody.infra.external.feign.dto.response.kakao.KakaoTokenResponse;
import com.clody.infra.external.feign.dto.response.kakao.KakaoUserInfoResponse;
import com.clody.infra.external.feign.kakao.KakaoAuthClient;
import com.clody.infra.external.feign.kakao.KakaoUserInfoClient;
import com.clody.support.dto.type.ErrorType;
import com.clody.support.exception.auth.SignInException;
import com.clody.support.exception.auth.SignUpException;
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
        KakaoTokenResponse token = getKakaoToken(info.authTokenWithBearer());
        String accessTokenWithPrefix = KakaoTokenResponse.getTokenWithPrefix(token.access_token());
        KakaoUserInfoResponse userInfo = getKakaoUserInfo(accessTokenWithPrefix);

    //로컬 테스트 아래의1줄 주석 하셈
//    KakaoUserInfoResponse userInfo = getKakaoUserInfo(info.authTokenWithBearer());

    validateDuplicateUser(userInfo);
    return UserSocialInfo.of(userInfo.id(), platform);
  }

//  public UserSocialInfo signIn(UserDomainSignInInfo info) {
//
//    Platform platform = info.platform();
//
//    //로컬 테스트시 아래의3줄 주석 해제
////        KakaoTokenResponse token = getKakaoToken(accessTokenWithBearer);
////        String accessTokenWithPrefix = KakaoTokenResponse.getTokenWithPrefix(token.access_token());
////        KakaoUserInfoResponse userInfo = getKakaoUserInfo(accessTokenWithPrefix);
//
//    //로컬 테스트시 아래의 1줄 주석 하셈
//    KakaoUserInfoResponse userInfo = getKakaoUserInfo(info.authTokenWithBearer());
//
////    return findByPlatformAndPlatformId(platform, userInfo.id());
//  }

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
