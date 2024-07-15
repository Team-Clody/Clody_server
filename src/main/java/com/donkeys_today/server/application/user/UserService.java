package com.donkeys_today.server.application.user;

import com.donkeys_today.server.application.auth.JwtUtil;
import com.donkeys_today.server.application.user.event.UserSignUpEvent;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.infrastructure.user.UserRepository;
import com.donkeys_today.server.presentation.auth.dto.response.TokenReissueResponse;
import com.donkeys_today.server.presentation.user.dto.requset.PatchUserNameRequest;
import com.donkeys_today.server.presentation.user.dto.requset.UserSignInRequest;
import com.donkeys_today.server.presentation.user.dto.requset.UserSignUpRequest;
import com.donkeys_today.server.presentation.user.dto.response.PatchUserNameResponse;
import com.donkeys_today.server.presentation.user.dto.response.UserInfoResponse;
import com.donkeys_today.server.presentation.user.dto.response.UserSignInResponse;
import com.donkeys_today.server.presentation.user.dto.response.UserSignUpResponse;
import com.donkeys_today.server.support.jwt.JwtProvider;
import com.donkeys_today.server.support.jwt.RefreshTokenRepository;
import com.donkeys_today.server.support.jwt.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserRetriever userRetriever;
  private final UserAuthenticator userAuthenticator;
  private final UserUpdater userUpdater;
  private final UserRemover userRemover;

  private final RefreshTokenRepository refreshTokenRepository;
  private final JwtProvider jwtProvider;
  private final ApplicationEventPublisher applicationEventPublisher;

  @Transactional
  public UserSignUpResponse signUp(final String authorizationCode,
      final UserSignUpRequest request) {
    User newUser = userAuthenticator.signUp(authorizationCode, request);
    User savedUser = userRepository.save(newUser);
    Token token = userAuthenticator.issueToken(savedUser.getId());
    applicationEventPublisher.publishEvent(new UserSignUpEvent(this, savedUser));
    return UserSignUpResponse.of(savedUser.getId(), token.accessToken(), token.refreshToken());
  }

  public UserSignInResponse signIn(final String authorizationCode,
      final UserSignInRequest request) {

    User foundUser = userAuthenticator.signIn(authorizationCode, request);
    Token token = userAuthenticator.issueToken(foundUser.getId());
    return UserSignInResponse.of(foundUser.getId(), token.accessToken(), token.refreshToken());
  }

    public TokenReissueResponse reissueAccessToken(String refreshToken) {
        return jwtProvider.getTokenReissueResponse(refreshToken);
    }

    public User getUserById(Long userId) {
        return userRetriever.findUserById(userId);
    }

    public UserInfoResponse getUserInfo() {

      User user = userRetriever.findUserById(JwtUtil.getLoginMemberId());
      return UserInfoResponse.of(user.getEmail(), user.getNickName(), user.getPlatform().getName());
    }

  public PatchUserNameResponse patchUserName(PatchUserNameRequest patchUserNameRequest) {

    User user = userUpdater.updateUserName(patchUserNameRequest.name());
    return PatchUserNameResponse.of(user.getNickName());
  }
}
