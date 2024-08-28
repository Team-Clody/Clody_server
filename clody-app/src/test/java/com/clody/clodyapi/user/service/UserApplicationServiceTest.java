package com.clody.clodyapi.user.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.clody.clodyapi.alarm.service.AlarmUpdateService;
import com.clody.clodyapi.user.controller.dto.request.UserSignUpRequest;
import com.clody.clodyapi.user.mapper.UserMapper;
import com.clody.domain.user.Platform;
import com.clody.domain.user.User;
import com.clody.domain.user.dto.UserDomainInfo;
import com.clody.domain.user.service.UserAuthService;
import com.clody.domain.user.strategy.SocialRegisterStrategyFactory;
import com.clody.domain.user.strategy.UserSocialInfo;
import com.clody.infra.models.user.strategy.KakaoAuthStrategy;
import com.clody.support.jwt.JwtProvider;
import com.clody.support.jwt.RefreshTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserApplicationServiceTest {

  @InjectMocks
  private UserApplicationService userApplicationService;

  @Mock
  private UserMapper userMapper;

  @Mock
  private SocialRegisterStrategyFactory socialRegisterStrategyFactory;

  @Mock
  private AlarmUpdateService alarmUpdateService;

  @Mock
  private UserAuthService userAuthService;

  @Mock
  private KakaoAuthStrategy kakaoAuthStrategy;

  @Mock
  private JwtProvider jwtProvider;

  @Mock
  private RefreshTokenRepository refreshTokenRepository;

  @Test
  void 회원가입_카카오_유저_회원가입_테스트() {
    // given
    var userSignUpRequest = new UserSignUpRequest("kakao", "test@naver.com", "John Doe", null,
        "testFcmToken");
    var authToken = "testAuthToken";
    var platform = Platform.KAKAO;
    var userDomainInfo = new UserDomainInfo(platform, null, authToken);
    var userSocialInfo = new UserSocialInfo("1L", platform);
    var newUser = User.createNewUser(userSocialInfo.id(), platform, userSignUpRequest.email(),
        userSignUpRequest.name());
    var savedUser = new User(); // 실제 User 객체 초기화 필요


    // when
    when(socialRegisterStrategyFactory.getStrategy(platform)).thenReturn(kakaoAuthStrategy);
    when(kakaoAuthStrategy.getUserInfo(userDomainInfo)).thenReturn(userSocialInfo);
    when(userAuthService.registerUser(any(User.class))).thenReturn(
        savedUser);

    var response = userApplicationService.signUp(userSignUpRequest, authToken);

    // then
    assertNotNull(response);
    verify(alarmUpdateService).updateFcmToken(userSignUpRequest.fcmToken());
  }

}
