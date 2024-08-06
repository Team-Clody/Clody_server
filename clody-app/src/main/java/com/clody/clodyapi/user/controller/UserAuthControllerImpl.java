package com.clody.clodyapi.user.controller;

import com.clody.clodyapi.user.controller.dto.request.UserSignInRequest;
import com.clody.clodyapi.user.controller.dto.request.UserSignUpRequest;
import com.clody.clodyapi.user.controller.dto.response.TokenReissueResponse;
import com.clody.clodyapi.user.controller.dto.response.UserAuthResponse;
import com.clody.clodyapi.user.service.UserApplicationService;
import com.clody.support.constants.HeaderConstants;
import com.clody.support.dto.ApiResponse;
import com.clody.support.dto.type.SuccessType;
import com.clody.support.jwt.JwtProvider;
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
  private final JwtProvider jwtProvider;

  @PostMapping("/auth/signup")
  public ResponseEntity<ApiResponse<UserAuthResponse>> signUp(
      @RequestHeader(HeaderConstants.AUTHORIZATION) final String accessTokenWithBearer,
      @RequestBody final UserSignUpRequest userSignUpRequest) {

//    jwtProvider.validateTokenStartsWithBearer(accessTokenWithBearer);
    final UserAuthResponse response = userApplicationService.signUp(userSignUpRequest,
        accessTokenWithBearer);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(SuccessType.CREATED_SUCCESS, response));
  }

  @ResponseBody
  @PostMapping("/auth/signin")
  public ResponseEntity<ApiResponse<UserAuthResponse>> signIn(
      @RequestHeader(HeaderConstants.AUTHORIZATION) String accessTokenWithBearer,
      @RequestBody final UserSignInRequest userSignInRequest) {
    jwtProvider.validateTokenStartsWithBearer(accessTokenWithBearer);
    final UserAuthResponse response = userApplicationService.signIn(userSignInRequest,
        accessTokenWithBearer);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
  }


///*
//kakao:
//  client-id: eb5b3511f81201dba4850861989793f6
//  client-secret: vQRSNrKrHg4PIFXJhrO2aW32PxLbA8AV
//  redirect-uri: https://clody.store/oauth/kakao
//*/
  @Override
  @GetMapping("/auth/reissue")
  public ResponseEntity<ApiResponse<TokenReissueResponse>> reissue(
      @RequestHeader(HeaderConstants.AUTHORIZATION) final String refreshTokenWithBearer) {

    jwtProvider.validateTokenStartsWithBearer(refreshTokenWithBearer);
    TokenReissueResponse response = userApplicationService.reissue(refreshTokenWithBearer);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
  }
}
