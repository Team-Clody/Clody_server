package com.donkeys_today.server.application.user;

import com.donkeys_today.server.application.auth.JwtUtil;
import com.donkeys_today.server.application.user.event.UserSignUpEvent;
import com.donkeys_today.server.common.constants.Constants;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.infrastructure.user.UserRepository;
import com.donkeys_today.server.presentation.auth.dto.response.TokenReissueResponse;
import com.donkeys_today.server.presentation.user.dto.requset.UserNamePatchRequest;
import com.donkeys_today.server.presentation.user.dto.requset.UserSignInRequest;
import com.donkeys_today.server.presentation.user.dto.requset.UserSignUpRequest;
import com.donkeys_today.server.presentation.user.dto.response.UserInfoResponse;
import com.donkeys_today.server.presentation.user.dto.response.UserNamePatchResponse;
import com.donkeys_today.server.presentation.user.dto.response.UserSignInResponse;
import com.donkeys_today.server.presentation.user.dto.response.UserSignUpResponse;
import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.UnauthorizedException;
import com.donkeys_today.server.support.jwt.JwtProvider;
import com.donkeys_today.server.support.jwt.RefreshTokenRepository;
import com.donkeys_today.server.support.jwt.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRetriever userRetriever;
  private final UserAuthenticator userAuthenticator;
  private final UserUpdater userUpdater;
  private final UserRemover userRemover;
  private final UserCreator userCreator;

  private final JwtProvider jwtProvider;
  private final ApplicationEventPublisher applicationEventPublisher;

  @Transactional
  public UserSignUpResponse signUp(final String authorizationCode,
      final UserSignUpRequest request) {
    User newUser = userAuthenticator.signUp(authorizationCode, request);
    User savedUser = userCreator.saveUser(newUser);
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

    public TokenReissueResponse reissueAccessToken(String refreshTokenWithBearer) {
      if(StringUtils.hasText(refreshTokenWithBearer) && refreshTokenWithBearer.startsWith(Constants.BEARER)) {
        String refreshToken = refreshTokenWithBearer.substring(Constants.BEARER.length());
        return jwtProvider.getTokenReissueResponse(refreshToken);
      }
      throw new UnauthorizedException(ErrorType.UNAUTHORIZED);

    }

    public User getUserById(Long userId) {
        return userRetriever.findUserById(userId);
    }

    public UserInfoResponse getUserInfo() {

      User user = userRetriever.findUserById(JwtUtil.getLoginMemberId());
      return UserInfoResponse.of(user.getEmail(), user.getNickName(), user.getPlatform().getName());
    }

  public UserNamePatchResponse patchUserName(UserNamePatchRequest userNamePatchRequest) {

    User user = userUpdater.updateUserName(userNamePatchRequest.name());
    return UserNamePatchResponse.of(user.getNickName());
  }
}
