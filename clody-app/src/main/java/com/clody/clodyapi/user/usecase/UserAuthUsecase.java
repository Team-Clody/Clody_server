package com.clody.clodyapi.user.usecase;

import com.clody.clodyapi.user.controller.dto.request.UserSignInRequest;
import com.clody.clodyapi.user.controller.dto.request.UserSignUpRequest;
import com.clody.clodyapi.user.controller.dto.response.TokenReissueResponse;
import com.clody.clodyapi.user.controller.dto.response.UserAuthResponse;

public interface UserAuthUsecase {
  UserAuthResponse signUp(UserSignUpRequest request, String authToken);

  UserAuthResponse signIn(UserSignInRequest request, String authToken);

  TokenReissueResponse reissue(String refreshToken);
}
