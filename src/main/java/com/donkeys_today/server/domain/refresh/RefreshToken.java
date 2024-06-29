package com.donkeys_today.server.domain.refresh;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(timeToLive = 60 * 1000L * 60 * 24 * 7 * 2)
@AllArgsConstructor
@Getter
@Builder
public class RefreshToken {

    @Id
    private Long id;

    private String userId;

    @Indexed
    private String refresh;

    private String expiration;

    private static RefreshToken of(
            final Long id,
            final String userId,
            final String refresh,
            final String expiration
    ) {
        return RefreshToken.builder().id(id).userId(userId).refresh(refresh).expiration(expiration)
                .build();
    }
}