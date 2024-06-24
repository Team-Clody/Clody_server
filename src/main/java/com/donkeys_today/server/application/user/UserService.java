package com.donkeys_today.server.application.user;

import com.donkeys_today.server.application.user.dto.request.UserSignUpRequest;
import com.donkeys_today.server.application.user.dto.response.UserResponse;
import com.donkeys_today.server.application.user.dto.response.UserSignUpResponse;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserCreater userCreater;
  private final UserRetriever userRetriever;
  private final UserUpdater userUpdater;
  private final UserRemover userRemover;

  @Transactional
  public UserSignUpResponse signUp(UserSignUpRequest request) {

    User user = User.builder()
        .userName(request.userName())
        .email(request.email())
        .phoneNum(request.phoneNum())
        .build();
    userRepository.save(user);
    return UserSignUpResponse.from(user);
  }

  public UserResponse getUser(Long userId){

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("no such user"));
    return UserResponse.from(user);
  }

}
