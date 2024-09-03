package com.clody.clodyapi.user.controller.dto.response;

import com.clody.domain.user.Platform;

public record UserInfoGetResponse(
        String email,
        String name,
        String platform
) {
    public static UserInfoGetResponse of(String email, String name, Platform platform){
        return new UserInfoGetResponse(email, name, platform.getName());
    }
}

