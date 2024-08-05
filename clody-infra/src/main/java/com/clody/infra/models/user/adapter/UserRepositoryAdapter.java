package com.clody.infra.models.user.adapter;

import com.clody.domain.user.Platform;
import com.clody.domain.user.User;
import com.clody.domain.user.repository.UserRepository;
import com.clody.infra.models.user.repository.JpaUserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

  private final JpaUserRepository userRepository;

  @Override
  public Optional<User> findByPlatformAndPlatformID(Platform platform, String platformId) {
    return userRepository.findByPlatformAndPlatformID(platform, platformId);
  }

  @Override
  public boolean existsByPlatformAndPlatformID(Platform platform, String platformId) {
    return userRepository.existsByPlatformAndPlatformID(platform, platformId);
  }

  @Override
  public User save(User user) {
    return userRepository.save(user);
  }

  @Override
  public Optional<User> findById(Long userId) {
    return userRepository.findById(userId);
  }

  @Override
  public void delete(User user) {
    userRepository.delete(user);
  }
}
