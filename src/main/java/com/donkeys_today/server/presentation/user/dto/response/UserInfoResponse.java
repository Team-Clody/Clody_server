package com.donkeys_today.server.presentation.user.dto.response;

public record UserInfoResponse(
        String email,
        String name,
        String platform
) {
    public static UserInfoResponse of(String email, String name, String platform){
        return new UserInfoResponse(email, name, platform);
    }
}
