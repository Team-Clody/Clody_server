package com.clody.clodyapi.user.controller.dto.response;

public record UserDeleteResponse(
        String email,
        String name
) {
    public static UserDeleteResponse of(String email, String name){
        return new UserDeleteResponse(email, name);
    }
}

