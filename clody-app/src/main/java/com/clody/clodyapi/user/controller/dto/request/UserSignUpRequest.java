package com.clody.clodyapi.user.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserSignUpRequest(
    @Schema(description = "로그인 플랫폼", example = "apple/kakao")
    String platform,
    String email,
    String name,
    String fcmToken
) {
}
