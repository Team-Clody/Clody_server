package com.donkeys_today.server.support.jwt;

import com.donkeys_today.server.presentation.auth.dto.response.TokenReissueResponse;

public interface JwtProvider {

    String issueAccessToken(Long userId);

    String issueRefreshToken(Long userId);

    Long getUserIdFromJwtSubject(String token);

    String generateToken(String type, Long userId, Long tokenExpirationTime);

    void validateAccessToken(String accessToken);

    void validateRefreshToken(String refreshToken);

    boolean equalsRefreshToken(String refreshToken, String savedRefreshToken);

    TokenReissueResponse getTokenReissueResponse(String refreshTokenWithBearer);
}
