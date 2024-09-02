package com.clody.clodyapi.diary.controller.dto.response;

public record DiaryCreatedTimeResponse(
    int HH,
    int mm,
    int ss,
    boolean isFirst
) {
    public static DiaryCreatedTimeResponse of(int HH, int mm, int ss, boolean isFirst) {
        return new DiaryCreatedTimeResponse(HH, mm, ss, isFirst);
    }
}
