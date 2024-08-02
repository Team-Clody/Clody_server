package com.clody.clodyapi.service.user;

import com.clody.domain.user.User;
import com.clody.domain.user.repository.UserRepository;
import com.clody.support.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserUpdater {

  private final UserRepository userRepository;

//  public void updateFcmToken(Long userId, String fcmToken) {
//    User user = userRepository.findById(userId)
//        .orElseThrow(() -> new BusinessException(ErrorType.USER_NOT_FOUND));
//    user.updateUserFcmToken(fcmToken);
//  }

  @Transactional
  public User updateUserName(String newName) {

    User user = userRepository.findById(JwtUtil.getLoginMemberId()).get();
    user.updateUserName(newName);

    return user;
  }
}
