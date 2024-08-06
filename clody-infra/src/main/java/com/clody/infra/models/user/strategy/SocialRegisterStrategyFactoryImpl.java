package com.clody.infra.models.user.strategy;

import com.clody.domain.user.Platform;
import com.clody.domain.user.strategy.SocialRegisterStrategy;
import com.clody.domain.user.strategy.SocialRegisterStrategyFactory;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class SocialRegisterStrategyFactoryImpl implements SocialRegisterStrategyFactory {

  private final Map<Platform, SocialRegisterStrategy> strategyMap = new HashMap<>();

  private final KakaoAuthStrategy kakaoAuthStrategy;
  private final GoogleAuthStrategy googleAuthStrategy;
  private final AppleAuthStrategy appleAuthStrategy;

  public SocialRegisterStrategyFactoryImpl(
      KakaoAuthStrategy kakaoAuthStrategy, GoogleAuthStrategy googleAuthStrategy,
      AppleAuthStrategy appleAuthStrategy) {
    this.kakaoAuthStrategy = kakaoAuthStrategy;
    this.googleAuthStrategy = googleAuthStrategy;
    this.appleAuthStrategy = appleAuthStrategy;
  }

  @PostConstruct
  public void initSocialLoginProvider() {
    strategyMap.put(Platform.KAKAO, kakaoAuthStrategy);
    strategyMap.put(Platform.GOOGLE, googleAuthStrategy);
    strategyMap.put(Platform.APPLE, appleAuthStrategy);
  }

  @Override
  public SocialRegisterStrategy getStrategy(Platform platform) {
    return strategyMap.get(platform);
  }
}
