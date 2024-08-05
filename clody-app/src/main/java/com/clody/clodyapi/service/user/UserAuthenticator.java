//package com.clody.clodyapi.service.user;
//
//import static com.clody.support.constants.JwtConstants.REFRESH_TOKEN_EXPIRATION_TIME;
//
//import com.clody.clodyapi.presentation.auth.dto.request.UserSignInRequest;
//import com.clody.clodyapi.presentation.auth.dto.request.UserSignUpRequest;
//import com.clody.domain.user.strategy.SocialRegisterStrategy;
//import com.clody.domain.user.Platform;
//import com.clody.domain.user.User;
//import com.clody.support.jwt.JwtProvider;
//import com.clody.support.jwt.RefreshTokenRepository;
//import com.clody.support.jwt.Token;
//import jakarta.annotation.PostConstruct;
//import java.util.HashMap;
//import java.util.Map;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class UserAuthenticator {
//
//  private final Map<Platform, SocialRegisterStrategy> provider = new HashMap<>();
//
//  private final KakaoAuthStrategy kakaoAuthStrategy;
//  private final GoogleAuthStrategy googleAuthStrategy;
//  private final AppleAuthStrategy appleAuthStrategy;
//  private final JwtProvider jwtProvider;
//  private final RefreshTokenRepository refreshTokenRepository;
//
//  @PostConstruct
//  public void initSocialLoginProvider() {
//    provider.put(Platform.KAKAO, kakaoAuthStrategy);
//    provider.put(Platform.GOOGLE, googleAuthStrategy);
//    provider.put(Platform.APPLE, appleAuthStrategy);
//  }
//
//  public User signUp(String authToken, UserSignUpRequest request) {
//    Platform platform = getPlatformFromRequestString(request.platform());
//    return provider.get(platform).signUp(request, authToken);
//  }
//
//  public User signIn(String authToken, UserSignInRequest request) {
//    Platform platform = getPlatformFromRequestString(request.platform());
//    return provider.get(platform).signIn(request, authToken);
//  }
//
//  public Token issueToken(Long id) {
//    String accessToken = jwtProvider.issueAccessToken(id);
//    String refreshToken = jwtProvider.issueRefreshToken(id);
//    storeRefreshToken(id, refreshToken);
//    return Token.of(accessToken, refreshToken);
//  }
//
//  private void storeRefreshToken(Long id, String refreshToken) {
//    refreshTokenRepository.saveRefreshToken(id, refreshToken, REFRESH_TOKEN_EXPIRATION_TIME);
//  }
//
//  private Platform getPlatformFromRequestString(String platform) {
//    return Platform.fromString(platform);
//  }
//}
