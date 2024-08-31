package com.clody.clodyapi.diary.usecase;

import com.clody.clodyapi.user.controller.dto.response.UserInfoGetResponse;

public interface UserRetrieverUsecase {

    UserInfoGetResponse retrieveUser();
}
