package com.donkeys_today.server.presentation.user.dto.response;

public record DiaryCreatedTimeGetResponse(
        int HH,
        int MM,
        int SS
) {
    public static DiaryCreatedTimeGetResponse of(int HH, int MM, int SS) {
        return new DiaryCreatedTimeGetResponse(HH, MM, SS);
    }
}

