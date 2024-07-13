package com.donkeys_today.server.presentation.user;

import com.donkeys_today.server.application.user.UserService;
import com.donkeys_today.server.common.constants.Constants;
import com.donkeys_today.server.presentation.api.UserController;
import com.donkeys_today.server.presentation.user.dto.requset.UserSignInRequest;
import com.donkeys_today.server.presentation.user.dto.requset.UserSignUpRequest;
import com.donkeys_today.server.presentation.user.dto.response.UserSignInResponse;
import com.donkeys_today.server.presentation.user.dto.response.UserSignUpResponse;
import com.donkeys_today.server.support.dto.ApiResponse;
import com.donkeys_today.server.support.dto.type.SuccessType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class UserControllerImpl implements UserController {

  private final UserService userService;

  @PostMapping("/auth/signup")
  public ResponseEntity<ApiResponse<UserSignUpResponse>> signUp(
      @RequestHeader(Constants.AUTHORIZATION) final String authorization_code,
      @RequestBody final UserSignUpRequest userSignUpRequest) {
    System.out.println(authorization_code);
    final UserSignUpResponse response = userService.signUp(authorization_code, userSignUpRequest);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(SuccessType.CREATED_SUCCESS, response));
  }

  @PostMapping("/auth/signin")
  public ResponseEntity<ApiResponse<UserSignInResponse>> signIn(
      @RequestHeader(Constants.AUTHORIZATION) String authorization_code,
      @RequestBody final UserSignInRequest userSignInRequest) {
    final UserSignInResponse response = userService.signIn(authorization_code, userSignInRequest);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
  }
}