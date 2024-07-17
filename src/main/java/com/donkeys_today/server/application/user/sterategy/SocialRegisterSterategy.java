package com.donkeys_today.server.application.user.sterategy;

import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.presentation.user.dto.requset.UserSignInRequest;
import com.donkeys_today.server.presentation.user.dto.requset.UserSignUpRequest;

public interface SocialRegisterSterategy {

  User signUp(UserSignUpRequest userSignUpRequest, String authToken);

  User signIn(UserSignInRequest userSignInRequest, String authToken);
}
