package com.donkeys_today.server.presentation.diary.dto.response;

public record DiaryContent(
        String content
) {
    public static DiaryContent of(String content) {
        return new DiaryContent(content);
    }
}
