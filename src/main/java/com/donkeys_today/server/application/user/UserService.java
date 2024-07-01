package com.donkeys_today.server.application.user;

import com.donkeys_today.server.application.user.dto.request.UserSignUpRequest;
import com.donkeys_today.server.domain.user.Platform;
import com.donkeys_today.server.domain.user.UserRepository;
import com.donkeys_today.server.support.feign.kakao.KakaoAuthClient;
import com.donkeys_today.server.support.jwt.JwtProvider;
import com.donkeys_today.server.support.jwt.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserCreater userCreater;
  private final UserRetriever userRetriever;
  private final UserUpdater userUpdater;
  private final UserRemover userRemover;

  private final RefreshTokenRepository refreshTokenRepository;
  private final JwtProvider jwtProvider;
  private final KakaoAuthClient kakaoAuthClient;

  public UserRegisterResponse register(String token, UserSignUpRequest request){
    Platform platform = get

  }

}
