package com.donkeys_today.server.presentation.user.dto.requset;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalTime;

public record UserSignUpRequest(
    @Schema(description = "로그인 플랫폼", example = "apple/kakao")
    String platform,
    String email,
    String name,
    @Schema(description = "애플 로그인", example = "ey23asdf.asdasdd.asdasd")
    String id_token
) {
}
