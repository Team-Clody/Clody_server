package com.donkeys_today.server.presentation.api;

import com.donkeys_today.server.common.constants.Constants;
import com.donkeys_today.server.presentation.auth.dto.response.TokenReissueResponse;
import com.donkeys_today.server.support.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증/인가")
@RequestMapping("/api/v1")
@RestController
public interface ReissueController {

    @Operation(summary = "토큰 재발급 ", description = "Refresh토큰으로 access토큰을 재발급 합니다.")
    @GetMapping("/auth/reissue")
    ResponseEntity<ApiResponse<TokenReissueResponse>> reissue(
            @RequestHeader(Constants.REFRESHTOKEN) @Parameter(name = "refreshToken", description = "리프레쉬토큰", required = true) final String refreshToken
    );


}
