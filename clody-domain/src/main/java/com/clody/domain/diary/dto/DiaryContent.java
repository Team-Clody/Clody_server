package com.clody.domain.diary.dto;

public record DiaryContent(
        String content
) {
    public static DiaryContent of(String content) {
        return new DiaryContent(content);
    }
}
