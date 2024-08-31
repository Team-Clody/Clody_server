package com.clody.clodyapi.diary.service;

import com.clody.clodyapi.diary.usecase.UserRetrieverUsecase;
import com.clody.clodyapi.user.controller.dto.response.UserInfoGetResponse;
import com.clody.clodyapi.user.mapper.UserMapper;
import com.clody.domain.diary.service.UserQueryService;
import com.clody.domain.user.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRetrieverService implements UserRetrieverUsecase {

  private final UserQueryService userService;
  @Override
  public UserInfoGetResponse retrieveUser() {

    UserInfo userInfo = userService.getUser();
    return UserMapper.toUserGetInfoResponse(userInfo);
  }
}
