package com.clody.domain.user.strategy;

import com.clody.domain.user.Platform;

public interface SocialRegisterStrategyFactory {

  SocialRegisterStrategy getStrategy(Platform platform);
}
