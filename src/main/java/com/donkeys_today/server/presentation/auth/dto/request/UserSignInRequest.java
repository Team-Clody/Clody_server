package com.donkeys_today.server.presentation.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserSignInRequest(
        @Schema(description = "플랫폼", example = "apple/kakao")
        String platform

) {

}
