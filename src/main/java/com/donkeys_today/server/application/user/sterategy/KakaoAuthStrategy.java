package com.donkeys_today.server.application.user.sterategy;

import com.donkeys_today.server.domain.user.Platform;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.domain.user.UserRepository;
import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.BusinessException;
import com.donkeys_today.server.support.feign.dto.response.KakaoTokenResponse;
import com.donkeys_today.server.support.feign.dto.response.KakaoUserInfoResponse;
import com.donkeys_today.server.support.feign.kakao.KakaoAuthClient;
import com.donkeys_today.server.support.feign.kakao.KakaoUserInfoClient;
import com.donkeys_today.server.support.jwt.JwtConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoAuthStrategy implements UserAuthSterategy {

  private final KakaoAuthClient kakaoAuthClient;
  private final KakaoUserInfoClient kakaoUserInfoClient;
  private final UserRepository userRepository;

//  @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
  private String kakaoClientId = "8593dd97c205dc46191dac0b285254ba";
//  @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
  private String kakaoClientSecret = "4BF7jUzzUq6s34BVXJNhYxWg4em0cTsx";
  private String redirect_uri = "http://localhost:8080/";

  @Override
  public User signUp(String authToken) {
    KakaoTokenResponse token = getKakaoToken(authToken);
    String accessTokenWithPrefix = KakaoTokenResponse.getTokenWithPrefix(token.accessToken());
    KakaoUserInfoResponse userInfo = getKakaoUserInfo(accessTokenWithPrefix);

    validateDuplicateUser(userInfo);
    return User.builder()
        .platformID(userInfo.id())
        .nickName(userInfo.kakaoAccount().name())
        .email(userInfo.kakaoAccount().email())
        .build();
  }

  @Override
  public User signIn(String authToken) {
    return null;
  }

  private KakaoTokenResponse getKakaoToken(String authToken) {
    return kakaoAuthClient.getOauth2AccessToken(
        JwtConstants.KAKAO_AUTHORIZATION_PARAM,
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
    if (userRepository.existsByPlatformAndPlatformID(Platform.KAKAO,userInfo.id())){
      throw new BusinessException(ErrorType.DUPLICATED_USER_ERROR);
    }
  }

  public String getRedirectUri() {
    return String.format("%s?client_id=%s&redirect_uri=%s&response_type=code",
        "https://kauth.kakao.com/oauth/authorize", kakaoClientId, redirect_uri);
  }
}
