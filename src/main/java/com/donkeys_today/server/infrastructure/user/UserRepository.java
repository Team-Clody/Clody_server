package com.donkeys_today.server.infrastructure.user;

import com.donkeys_today.server.domain.user.Platform;
import com.donkeys_today.server.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByPlatformAndPlatformID(Platform platform, String platformID);

  Optional<User> findByPlatformAndPlatformID(Platform platform, String platformID);
}
