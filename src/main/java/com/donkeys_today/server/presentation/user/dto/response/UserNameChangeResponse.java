package com.donkeys_today.server.presentation.user.dto.response;

public record UserNameChangeResponse(String name) {

    public static UserNameChangeResponse of(String name) {
        return new UserNameChangeResponse(name);
    }

}
