package com.clody.clodyapi.diary.usecase;

import com.clody.clodyapi.user.controller.dto.request.UserNamePatchRequest;
import com.clody.clodyapi.user.controller.dto.response.UserNamePatchResponse;

public interface UserUpdateUsecase {

  UserNamePatchResponse updateUserName(UserNamePatchRequest userNamePatchRequest);

}
