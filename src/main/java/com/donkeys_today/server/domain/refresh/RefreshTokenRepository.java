package com.donkeys_today.server.domain.refresh;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

    Boolean existsByRefresh(String refresh);

    RefreshToken findByRefresh(String refresh);

    @Transactional
    void deleteByRefresh(String refresh);
}
