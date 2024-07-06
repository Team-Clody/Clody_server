package com.donkeys_today.server.presentation.user.dto.requset;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserSignInRequest(
    @Schema(description = "플랫폼", example = "apple/kakao")
    String platform
) {

}
