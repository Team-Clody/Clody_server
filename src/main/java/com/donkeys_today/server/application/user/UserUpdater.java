package com.donkeys_today.server.application.user;

import com.donkeys_today.server.application.auth.JwtUtil;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.infrastructure.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserUpdater {

  private final UserRepository userRepository;
  private final UserRetriever userRetriever;

//  public void updateFcmToken(Long userId, String fcmToken) {
//    User user = userRepository.findById(userId)
//        .orElseThrow(() -> new BusinessException(ErrorType.USER_NOT_FOUND));
//    user.updateUserFcmToken(fcmToken);
//  }

  @Transactional
  public User updateUserName(String newName) {

    User user = userRetriever.findUserById(JwtUtil.getLoginMemberId());
    user.updateUserName(newName);

    return user;
  }
}
