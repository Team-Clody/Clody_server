package com.clody.clodyapi.diary.controller.dto.response;

public record DiaryContent(
        String content
) {
    public static DiaryContent of(String content) {
        return new DiaryContent(content);
    }
}
