package com.donkeys_today.server.infrastructure.refreshToken;

import com.donkeys_today.server.support.jwt.JwtConstants;
import com.donkeys_today.server.support.jwt.RefreshTokenRepository;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisRefreshTokenRepository implements RefreshTokenRepository {

  private final RedisTemplate<String, String> redisTemplate;

  @Override
  public void saveRefreshToken(Long userId, String refreshToken, Long expirationTime) {
    String key = JwtConstants.REFRESH_TOKEN_PREFIX + userId;
    redisTemplate.opsForValue().set(
        key,
        refreshToken,
        expirationTime,
        TimeUnit.MILLISECONDS
    );
  }

  @Override
  public boolean hasRefreshToken(String userId) {
    String key = JwtConstants.REFRESH_TOKEN_PREFIX + userId;
    return redisTemplate.hasKey(key);
  }

  @Override
  public String getRefreshToken(String userId) {
    String key = JwtConstants.REFRESH_TOKEN_PREFIX + userId;
    return redisTemplate.opsForValue().get(key);
  }
}
