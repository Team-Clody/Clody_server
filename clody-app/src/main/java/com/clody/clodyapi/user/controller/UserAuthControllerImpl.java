package com.clody.clodyapi.user.controller;

import com.clody.clodyapi.user.controller.dto.request.UserSignInRequest;
import com.clody.clodyapi.user.controller.dto.request.UserSignUpRequest;
import com.clody.clodyapi.user.controller.dto.response.TokenReissueResponse;
import com.clody.clodyapi.user.controller.dto.response.UserAuthResponse;
import com.clody.clodyapi.user.service.UserApplicationService;
import com.clody.support.constants.HeaderConstants;
import com.clody.support.dto.ApiResponse;
import com.clody.support.dto.type.SuccessType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class UserAuthControllerImpl implements AuthController {

  private final UserApplicationService userApplicationService;

  @PostMapping("/auth/signup")
  public ResponseEntity<ApiResponse<UserAuthResponse>> signUp(
      @RequestHeader(HeaderConstants.AUTHORIZATION) final String tokenWithBearer,
      @RequestBody final UserSignUpRequest userSignUpRequest) {
    final UserAuthResponse response = userApplicationService.signUp(userSignUpRequest,
            tokenWithBearer);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(SuccessType.CREATED_SUCCESS, response));
  }

  @ResponseBody
  @PostMapping("/auth/signin")
  public ResponseEntity<ApiResponse<UserAuthResponse>> signIn(
      @RequestHeader(HeaderConstants.AUTHORIZATION) String tokenWithBearer,
      @RequestBody final UserSignInRequest userSignInRequest) {
    final UserAuthResponse response = userApplicationService.signIn(userSignInRequest,
            tokenWithBearer);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
  }

  @Override
  @GetMapping("/auth/reissue")
  public ResponseEntity<ApiResponse<TokenReissueResponse>> reissue(
      @RequestHeader(HeaderConstants.AUTHORIZATION) final String refreshTokenWithBearer) {
    TokenReissueResponse response = userApplicationService.reissue(refreshTokenWithBearer);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
  }
}
