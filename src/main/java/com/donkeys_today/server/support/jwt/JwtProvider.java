package com.donkeys_today.server.support.jwt;

import com.donkeys_today.server.common.constants.Constants;
import com.donkeys_today.server.presentation.auth.dto.response.TokenReissueResponse;
import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.UnauthorizedException;

public interface JwtProvider {

    String issueAccessToken(Long userId);

    String issueRefreshToken(Long userId);

    Long getUserIdFromJwtSubject(String token);

    String generateToken(String type, Long userId, Long tokenExpirationTime);

    void validateAccessToken(String accessToken);

    void validateRefreshToken(String refreshToken);

    boolean equalsRefreshToken(String refreshToken, String savedRefreshToken);

    TokenReissueResponse getTokenReissueResponse(String refreshTokenWithBearer);

    void validateTokenStartsWithBearer(String tokenWithBearer);
}
