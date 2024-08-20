package com.clody.clodyapi.diary.controller.dto.response;

public record DiaryCreatedTimeResponse(
    int HH,
    int mm,
    int ss
) {
    public static DiaryCreatedTimeResponse of(int HH, int mm, int ss) {
        return new DiaryCreatedTimeResponse(HH, mm, ss);
    }
}
