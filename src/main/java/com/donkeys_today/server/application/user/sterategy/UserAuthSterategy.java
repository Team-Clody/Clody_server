package com.donkeys_today.server.application.user.sterategy;

import com.donkeys_today.server.domain.user.User;

public interface UserAuthSterategy {

  User signUp(String authToken);

  User signIn(String authToken);
}
