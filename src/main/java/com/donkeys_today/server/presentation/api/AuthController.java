package com.donkeys_today.server.presentation.api;

import com.donkeys_today.server.common.constants.Constants;
import com.donkeys_today.server.presentation.auth.dto.response.TokenReissueResponse;
import com.donkeys_today.server.presentation.user.dto.requset.UserSignInRequest;
import com.donkeys_today.server.presentation.user.dto.requset.UserSignUpRequest;
import com.donkeys_today.server.presentation.user.dto.response.UserSignInResponse;
import com.donkeys_today.server.presentation.user.dto.response.UserSignUpResponse;
import com.donkeys_today.server.support.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증/인가")
@RequestMapping("/api/v1")
@RestController
public interface AuthController {

    @Operation(summary = "유저 회원가입 ", description = "Authorization code 와 UserSignUpRequest 로 회원가입을 진행합니다.")
    @PostMapping("/auth/signup")
    ResponseEntity<ApiResponse<UserSignUpResponse>> signUp(
            @RequestHeader(Constants.AUTHORIZATION) @Parameter(name = "Authorization", description = "클라이언트가 인증 서버로부터 받은 인증 코드", required = true) final String auth_code,
            @RequestBody(
                    description = "회원가입을 위하여 부가적으로 받는 정보 객체",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserSignUpRequest.class))
            ) final UserSignUpRequest request
    );

    @Operation(summary = "유저 로그인 ", description = "Authorization code로 로그인을 진행합니다.")
    @PostMapping("/auth/signin")
    ResponseEntity<ApiResponse<UserSignInResponse>> signIn(
            @RequestHeader(Constants.AUTHORIZATION) @Parameter(name = "Authorization", description = "클라이언트가 인증 서버로부터 받은 인증 코드", required = true) final String auth_code,
            @RequestBody(
                    description = "로그인을 위하여 부가적으로 받는 정보 객체",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserSignInRequest.class))
            ) final UserSignInRequest userSignInRequest
    );

    @Operation(summary = "토큰 재발급 ", description = "Refresh토큰으로 access토큰을 재발급 합니다.")
    @GetMapping("/auth/reissue")
    ResponseEntity<ApiResponse<TokenReissueResponse>> reissue(HttpServletRequest request,
                                                              @CookieValue(name = Constants.REFRESHTOKEN) @Parameter(name = "refreshToken", description = "리프레쉬토큰", required = true) final String refreshTokenWithBearer
    );

}
