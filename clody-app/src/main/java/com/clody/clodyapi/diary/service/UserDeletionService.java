package com.clody.clodyapi.diary.service;

import com.clody.clodyapi.diary.usecase.UserDeletionUsecase;
import com.clody.clodyapi.user.controller.dto.response.UserDeleteResponse;
import com.clody.clodyapi.user.mapper.UserMapper;
import com.clody.domain.user.User;
import com.clody.domain.user.dto.UserInfo;
import com.clody.domain.user.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDeletionService implements UserDeletionUsecase {

  private final UserAuthService userAuthService;

  public UserDeleteResponse deleteUser() {
    User user = userAuthService.deleteUser();
    return UserMapper.toUserDeleteResponse(
            UserInfo.toUserInfo(user.getEmail(), user.getNickName(), user.getPlatform()));
  }
}
