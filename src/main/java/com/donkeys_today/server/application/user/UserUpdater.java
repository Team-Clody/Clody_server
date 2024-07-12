package com.donkeys_today.server.application.user;

import com.donkeys_today.server.infrastructure.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUpdater {

  private final UserRepository userRepository;

//  public void updateFcmToken(Long userId, String fcmToken) {
//    User user = userRepository.findById(userId)
//        .orElseThrow(() -> new BusinessException(ErrorType.USER_NOT_FOUND));
//    user.updateUserFcmToken(fcmToken);
//  }

}
