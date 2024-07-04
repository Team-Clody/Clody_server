package com.donkeys_today.server.domain.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByPlatformAndPlatformID(Platform platform, Long platformID);
  Optional<User> findByPlatformAndPlatformID(Platform platform, Long platformID);
}
