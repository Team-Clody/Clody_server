package com.donkeys_today.server.support.jwt;

public interface JwtProvider {

  String issueAccessToken(Long userId);

  String issueRefreshToken(Long userId);

  Long getUserIdFromJwtSubject(String token);

  String generateToken(String type, Long userId, Long tokenExpirationTime);

  void validateAccessToken(String accessToken);

  void validateRefreshToken(String refreshToken);

  void equalsRefreshToken(String refreshToken, String savedRefreshToken);
}
