package com.donkeys_today.server.presentation.user.dto.response;

public record UserNamePatchResponse(
        String name
) {
    public static UserNamePatchResponse of(String name) {
        return new UserNamePatchResponse(name);
    }
}

