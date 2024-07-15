package com.donkeys_today.server.presentation.user.dto.response;

public record PatchUserNameResponse(
        String name
) {
    public static PatchUserNameResponse of(String name) {
        return new PatchUserNameResponse(name);
    }
}

