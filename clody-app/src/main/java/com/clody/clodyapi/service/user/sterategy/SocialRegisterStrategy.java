package com.clody.clodyapi.service.user.sterategy;


import com.clody.clodyapi.presentation.auth.dto.request.UserSignInRequest;
import com.clody.clodyapi.presentation.auth.dto.request.UserSignUpRequest;
import com.clody.domain.user.User;

public interface SocialRegisterStrategy {

  User signUp(UserSignUpRequest userSignUpRequest, String authToken);

  User signIn(UserSignInRequest userSignInRequest, String authToken);
}
