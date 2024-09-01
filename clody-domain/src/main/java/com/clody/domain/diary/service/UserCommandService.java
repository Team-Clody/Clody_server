package com.clody.domain.diary.service;

import com.clody.domain.user.User;
import com.clody.domain.user.dto.UserInfo;
import com.clody.domain.user.repository.UserRepository;
import com.clody.support.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandService {

  private final UserRepository userRepository;

  @Transactional
  public UserInfo patchName(String name) {

    User user = userRepository.findById(JwtUtil.getLoginMemberId());
    user.updateUserName(name);
    return UserInfo.toUserInfo(user.getEmail(), user.getNickName(), user.getPlatform());
  }
}
