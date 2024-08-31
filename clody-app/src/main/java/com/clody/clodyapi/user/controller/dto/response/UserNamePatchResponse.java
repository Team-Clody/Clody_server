package com.clody.clodyapi.user.controller.dto.response;

public record UserNamePatchResponse(
        String name
) {
    public static UserNamePatchResponse of(String name) {
        return new UserNamePatchResponse(name);
    }
}

