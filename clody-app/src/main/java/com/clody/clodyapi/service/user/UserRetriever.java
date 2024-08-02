package com.clody.clodyapi.service.user;

import com.clody.support.dto.type.ErrorType;
import com.clody.support.exception.NotFoundException;
import com.clody.domain.user.User;
import com.clody.domain.user.repository.UserRepository;
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
