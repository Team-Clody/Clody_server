package com.clody.domain.diary.service;

import com.clody.domain.user.User;
import com.clody.domain.user.dto.UserInfo;
import com.clody.domain.user.repository.UserRepository;
import com.clody.support.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class UserQueryService {

  private final UserRepository userRepository;

  public UserInfo getUser() {
    User user = userRepository.findById(JwtUtil.getLoginMemberId());
    return UserInfo.toUserInfo(user.getEmail(), user.getNickName(), user.getPlatform());
  }

}
