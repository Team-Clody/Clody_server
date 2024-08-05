package com.clody.clodyapi.user.config;

import com.clody.domain.user.strategy.SocialRegisterStrategyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StrategyConfig {

  private final SocialRegisterStrategyFactory strategyFactory;

  public StrategyConfig(SocialRegisterStrategyFactory strategyFactory) {
    this.strategyFactory = strategyFactory;
  }

  @Bean
  public SocialRegisterStrategyFactory socialRegisterStrategyFactory() {
    return this.strategyFactory;
  }
}
