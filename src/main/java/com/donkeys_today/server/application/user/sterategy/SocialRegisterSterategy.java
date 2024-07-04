package com.donkeys_today.server.application.user.sterategy;

import com.donkeys_today.server.domain.user.Platform;
import com.donkeys_today.server.domain.user.User;

public interface SocialRegisterSterategy {

  User signUp(Platform platform, String authToken);

  User signIn(Platform platform, String authToken);
}
