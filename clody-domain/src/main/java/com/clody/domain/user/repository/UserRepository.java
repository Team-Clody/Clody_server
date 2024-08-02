package com.clody.domain.user.repository;

import com.clody.domain.user.Platform;
import com.clody.domain.user.User;
import java.util.Optional;

public interface UserRepository {

  Optional<User> findByPlatformAndPlatformID(Platform platform, String platformId);

  boolean existsByPlatformAndPlatformID(Platform platform, String platformId);

  Optional<User> findById(Long userId);

  void delete(User user);

  User save(User user);
}
