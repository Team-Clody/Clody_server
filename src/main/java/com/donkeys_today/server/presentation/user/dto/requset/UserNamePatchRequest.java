package com.donkeys_today.server.presentation.user.dto.requset;

public record UserNamePatchRequest(
        String name
) {
    public static UserNamePatchRequest of(String name) {
        return new UserNamePatchRequest(name);
    }
}

