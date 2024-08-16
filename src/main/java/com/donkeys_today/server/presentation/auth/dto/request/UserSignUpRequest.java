package com.donkeys_today.server.presentation.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserSignUpRequest(
    @Schema(description = "로그인 플랫폼", example = "apple/kakao")
    String platform,
    String email,
    String name

) {
}
