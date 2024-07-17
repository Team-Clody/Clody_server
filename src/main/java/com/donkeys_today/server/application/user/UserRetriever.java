package com.donkeys_today.server.application.user;

import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.infrastructure.user.UserRepository;
import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRetriever {

  private final UserRepository userRepository;

  public User findUserById(final Long userId) {
    return userRepository.findById(userId).orElseThrow(
        ()-> new NotFoundException(ErrorType.USER_NOT_FOUND)
    );
  }
}
