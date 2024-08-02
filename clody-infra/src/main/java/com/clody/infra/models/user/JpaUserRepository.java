package com.clody.infra.models.user;

import com.clody.domain.user.Platform;
import com.clody.domain.user.User;
import com.clody.domain.user.repository.UserRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {

  Optional<User> findByPlatformAndPlatformID(Platform platform, String platformID);

  boolean existsByPlatformAndPlatformID(Platform platform, String platformID);
}
