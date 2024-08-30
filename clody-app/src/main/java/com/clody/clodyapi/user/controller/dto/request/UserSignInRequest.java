package com.clody.clodyapi.user.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserSignInRequest(
    @Schema(description = "플랫폼", example = "apple/kakao")
    String platform,
    String fcmToken
) {

}
