package com.donkeys_today.server.presentation.auth;

import com.donkeys_today.server.common.constants.Constants;
import com.donkeys_today.server.presentation.api.ReissueController;
import com.donkeys_today.server.presentation.auth.dto.response.TokenReissueResponse;
import com.donkeys_today.server.support.dto.ApiResponse;
import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.dto.type.SuccessType;
import com.donkeys_today.server.support.exception.BusinessException;
import com.donkeys_today.server.support.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReissueControllerImpl implements ReissueController {

    private final JwtProvider jwtProvider;

    @Override
    @GetMapping("/auth/reissue")
    public ResponseEntity<ApiResponse<TokenReissueResponse>> reissue(String refreshToken) {
        if (StringUtils.hasText(refreshToken) && refreshToken.startsWith(Constants.BEARER)) {
            String token = refreshToken.substring(Constants.BEARER.length());
            jwtProvider.validateRefreshToken(token);
            Long userId = jwtProvider.getUserIdFromJwtSubject(token);
            TokenReissueResponse response = TokenReissueResponse.of(jwtProvider.issueAccessToken(userId),
                    jwtProvider.issueRefreshToken(userId));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
        }
        throw new BusinessException(ErrorType.INVALID_REFRESH_TOKEN);
    }
}
