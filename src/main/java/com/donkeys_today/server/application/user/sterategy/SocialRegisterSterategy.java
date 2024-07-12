package com.donkeys_today.server.application.user.sterategy;

import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.presentation.auth.dto.request.UserSignInRequest;
import com.donkeys_today.server.presentation.auth.dto.request.UserSignUpRequest;

public interface SocialRegisterSterategy {

    User signUp(UserSignUpRequest userSignUpRequest, String authToken);

    User signIn(UserSignInRequest userSignInRequest, String authToken);
}
