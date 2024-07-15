package com.donkeys_today.server.presentation.user.dto.requset;

public record PatchUserNameRequest(
        String name
) {
    public static PatchUserNameRequest of(String name) {
        return new PatchUserNameRequest(name);
    }
}


