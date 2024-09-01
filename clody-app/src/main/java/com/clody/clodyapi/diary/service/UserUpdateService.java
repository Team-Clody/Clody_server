package com.clody.clodyapi.diary.service;

import com.clody.clodyapi.diary.usecase.UserUpdateUsecase;
import com.clody.clodyapi.user.controller.dto.request.UserNamePatchRequest;
import com.clody.clodyapi.user.controller.dto.response.UserNamePatchResponse;
import com.clody.clodyapi.user.mapper.UserMapper;
import com.clody.domain.diary.service.UserCommandService;
import com.clody.domain.user.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUpdateService implements UserUpdateUsecase {

  private final UserCommandService userCommandService;

  @Override
  public UserNamePatchResponse updateUserName(UserNamePatchRequest userNamePatchRequest) {

    UserInfo userInfo = userCommandService.patchName(userNamePatchRequest.name());
    return UserMapper.toUserNamePatchResponse(userInfo);
  }
}
