package com.clody.domain.user.service;

import com.clody.domain.user.Platform;
import com.clody.domain.user.User;
import com.clody.domain.user.event.UserCreatedEvent;
import com.clody.domain.user.event.UserEventPublisher;
import com.clody.domain.user.repository.UserRepository;
import com.clody.support.dto.type.ErrorType;
import com.clody.support.exception.BusinessException;
import com.clody.support.exception.auth.SignInException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

  private final UserRepository userRepository;
  private final UserEventPublisher userEventPublisher;

  public UserAuthService(UserRepository userRepository, UserEventPublisher userEventPublisher) {
    this.userRepository = userRepository;
    this.userEventPublisher = userEventPublisher;
  }

  public User registerUser(User user) {
    validateDuplicateUser(user);
    User registeredUser = userRepository.save(user);
    userEventPublisher.publishUserCreatedEvent(new UserCreatedEvent(registeredUser));
    return userRepository.save(user);
  }

  private void validateDuplicateUser(User user) {
    if (userRepository.existsByPlatformAndPlatformID(user.getPlatform(), user.getPlatformID())) {
      throw new BusinessException(ErrorType.DUPLICATED_USER_ERROR);
    }
  }

  public User findUserByPlatformAndPlatformId(Platform platform, String platformId) {
    return userRepository.findByPlatformAndPlatformID(platform, platformId).orElseThrow(
        () -> new SignInException(ErrorType.USER_NOT_FOUND)
    );
  }
}
