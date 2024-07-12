package com.donkeys_today.server.presentation.auth;

import com.donkeys_today.server.application.user.UserService;
import com.donkeys_today.server.common.constants.Constants;
import com.donkeys_today.server.presentation.api.AuthController;
import com.donkeys_today.server.presentation.auth.dto.request.UserSignInRequest;
import com.donkeys_today.server.presentation.auth.dto.request.UserSignUpRequest;
import com.donkeys_today.server.presentation.auth.dto.response.TokenReissueResponse;
import com.donkeys_today.server.presentation.auth.dto.response.UserSignInResponse;
import com.donkeys_today.server.presentation.auth.dto.response.UserSignUpResponse;
import com.donkeys_today.server.support.dto.ApiResponse;
import com.donkeys_today.server.support.dto.type.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
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
public class AuthControllerImpl implements AuthController {
    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<ApiResponse<UserSignUpResponse>> signUp(
            @RequestHeader(Constants.AUTHORIZATION) final String authorization_code,
            @RequestBody final UserSignUpRequest userSignUpRequest) {
        final UserSignUpResponse response = userService.signUp(authorization_code, userSignUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(SuccessType.CREATED_SUCCESS, response));
    }

    @ResponseBody
    @PostMapping("/auth/signin")
    public ResponseEntity<ApiResponse<UserSignInResponse>> signIn(
            @RequestHeader(Constants.AUTHORIZATION) String authorization_code,
            @RequestBody final UserSignInRequest userSignInRequest) {
        final UserSignInResponse response = userService.signIn(authorization_code, userSignInRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
    }

    @Override
    @GetMapping("/auth/reissue")
    public ResponseEntity<ApiResponse<TokenReissueResponse>> reissue(
            @CookieValue(name = Constants.REFRESHTOKEN) final String refreshToken) {
        TokenReissueResponse response = userService.reissueAccessToken(refreshToken);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(SuccessType.OK_SUCCESS, response));

    }
}
