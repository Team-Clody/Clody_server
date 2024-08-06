package com.clody.clodyapi.service.user;

import com.clody.clodyapi.user.controller.dto.response.TokenReissueResponse;
import com.clody.clodyapi.presentation.user.dto.requset.UserNamePatchRequest;
import com.clody.clodyapi.presentation.user.dto.response.UserDeleteResponse;
import com.clody.clodyapi.presentation.user.dto.response.UserInfoResponse;
import com.clody.clodyapi.presentation.user.dto.response.UserNamePatchResponse;
import com.clody.domain.user.User;
import com.clody.support.constants.HeaderConstants;
import com.clody.support.jwt.JwtProvider;
import com.clody.support.jwt.Token;
import com.clody.support.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRetriever userRetriever;
//  private final UserAuthenticator userAuthenticator;
  private final UserUpdater userUpdater;
  private final UserRemover userRemover;
  private final UserCreator userCreator;

  private final JwtProvider jwtProvider;
  private final ApplicationEventPublisher applicationEventPublisher;

//  @Transactional
//  public UserSignUpResponse signUp(final String accessTokenWithBearer,
//      final UserSignUpRequest request) {
//    User newUser = userAuthenticator.signUp(accessTokenWithBearer, request);
//    User savedUser = userCreator.saveUser(newUser);
//    Token token = userAuthenticator.issueToken(savedUser.getId());
//    applicationEventPublisher.publishEvent(new UserSignUpEvent(this, savedUser));
//    return UserSignUpResponse.of(savedUser.getId(), token.accessToken(), token.refreshToken());
//  }
//
//  public UserSignInResponse signIn(final String accessTokenWithBearer,
//      final UserSignInRequest request) {
//
//    User foundUser = userAuthenticator.signIn(accessTokenWithBearer, request);
//    Token token = userAuthenticator.issueToken(foundUser.getId());
//    return UserSignInResponse.of(foundUser.getId(), token.accessToken(), token.refreshToken());
//  }

  public TokenReissueResponse reissueAccessToken(String refreshTokenWithBearer) {
    String refreshToken = refreshTokenWithBearer.substring(HeaderConstants.BEARER.length());
    Token token = jwtProvider.getTokenReissueResponse(refreshToken);
    return TokenReissueResponse.of(token.accessToken(),token.refreshToken());

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

  public UserDeleteResponse deleteUser() {
    User user = userRemover.deleteUserById(JwtUtil.getLoginMemberId());
    return UserDeleteResponse.of(user.getEmail(), user.getNickName());
  }
}