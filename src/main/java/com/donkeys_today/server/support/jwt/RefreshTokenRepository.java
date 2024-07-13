package com.donkeys_today.server.support.jwt;

public interface RefreshTokenRepository {

    void saveRefreshToken(Long userId, String refreshToken, Long expirationTime);

    boolean hasRefreshToken(String key);

    String getRefreshToken(String key);
}
