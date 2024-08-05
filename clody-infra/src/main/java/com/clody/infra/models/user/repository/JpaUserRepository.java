package com.clody.infra.models.user.repository;

import com.clody.domain.user.Platform;
import com.clody.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long> {

  Optional<User> findByPlatformAndPlatformID(Platform platform, String platformID);

  boolean existsByPlatformAndPlatformID(Platform platform, String platformID);

  Optional<User> findById(Long userId);

  void delete(User user);

  User save(User user);
}
