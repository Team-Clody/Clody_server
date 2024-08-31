package com.clody.clodyapi.user.controller.dto.request;

public record UserNamePatchRequest(
        String name
) {
    public static UserNamePatchRequest of(String name) {
        return new UserNamePatchRequest(name);
    }
}


