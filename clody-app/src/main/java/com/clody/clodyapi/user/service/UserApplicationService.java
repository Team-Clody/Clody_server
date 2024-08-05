package com.clody.clodyapi.user.service;

import static com.clody.support.constants.JwtConstants.REFRESH_TOKEN_EXPIRATION_TIME;

import com.clody.clodyapi.user.controller.dto.request.UserSignInRequest;
import com.clody.clodyapi.user.controller.dto.request.UserSignUpRequest;
import com.clody.clodyapi.user.controller.dto.response.TokenReissueResponse;
import com.clody.clodyapi.user.controller.dto.response.UserAuthResponse;
import com.clody.clodyapi.user.mapper.UserMapper;
import com.clody.clodyapi.user.usecase.UserAuthUsecase;
import com.clody.domain.user.Platform;
import com.clody.domain.user.User;
import com.clody.domain.user.dto.UserDomainInfo;
import com.clody.domain.user.service.UserAuthService;
import com.clody.domain.user.strategy.SocialRegisterStrategy;
import com.clody.domain.user.strategy.SocialRegisterStrategyFactory;
import com.clody.domain.user.strategy.UserSocialInfo;
import com.clody.support.jwt.JwtProvider;
import com.clody.support.jwt.RefreshTokenRepository;
import com.clody.support.jwt.Token;
import org.springframework.stereotype.Service;

@Service
public class UserApplicationService implements UserAuthUsecase {

  private final SocialRegisterStrategyFactory strategyFactory;
  private final UserAuthService userAuthService;
  private final JwtProvider jwtProvider;
  private final RefreshTokenRepository refreshTokenRepository;

  public UserApplicationService(SocialRegisterStrategyFactory strategyFactory,
      UserAuthService userAuthService,
      JwtProvider jwtProvider,
      RefreshTokenRepository refreshTokenRepository) {
    this.strategyFactory = strategyFactory;
    this.userAuthService = userAuthService;
    this.jwtProvider = jwtProvider;
    this.refreshTokenRepository = refreshTokenRepository;
  }

  @Override
  public UserAuthResponse signUp(UserSignUpRequest request, String authToken) {
    Platform platform = Platform.fromString(request.platform());
    UserDomainInfo info = UserMapper.toDomainInfo(request,authToken);
    SocialRegisterStrategy strategy = strategyFactory.getStrategy(platform);
    UserSocialInfo userSocialInfo = strategy.getUserInfo(info);
    User newUser = User.createNewUser(userSocialInfo.id(), platform, request.email(), request.name());
    User savedUser = userAuthService.registerUser(newUser);
    Token token = issueToken(savedUser.getId());
    return UserAuthResponse.of(savedUser.getId(), token.accessToken(),token.refreshToken());
  }

  @Override
  public UserAuthResponse signIn(UserSignInRequest request, String authToken) {
    Platform platform = Platform.fromString(request.platform());
    UserDomainInfo info = UserMapper.toDomainInfo(request,authToken);
    SocialRegisterStrategy strategy = strategyFactory.getStrategy(platform);
    UserSocialInfo userSocialInfo = strategy.getUserInfo(info);
    User foundUser = userAuthService.findUserByPlatformAndPlatformId(platform, userSocialInfo.id());
    Token token = issueToken(foundUser.getId());
    return UserAuthResponse.of(foundUser.getId(), token.accessToken(),token.refreshToken());
  }

  @Override
  public TokenReissueResponse reissue(String refreshToken) {
    Token token = jwtProvider.getTokenReissueResponse(refreshToken);
    return TokenReissueResponse.of(token.accessToken(),token.refreshToken());

  }

  public Token issueToken(Long id) {
    String accessToken = jwtProvider.issueAccessToken(id);
    String refreshToken = jwtProvider.issueRefreshToken(id);
    storeRefreshToken(id, refreshToken);
    return Token.of(accessToken, refreshToken);
  }

  private void storeRefreshToken(Long id, String refreshToken) {
    refreshTokenRepository.saveRefreshToken(id, refreshToken, REFRESH_TOKEN_EXPIRATION_TIME);
  }

}
