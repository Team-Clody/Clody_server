package com.clody.domain.diary.dto;

import com.clody.domain.user.Platform;

public record UserDeletionInfo(
    String email,
    String name,
    Platform platform

) {
    public static UserDeletionInfo of(String email, String name, Platform platform) {
        return new UserDeletionInfo(email, name, platform);
    }
}
