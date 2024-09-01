package com.clody.clodyapi.diary.controller.dto.response;

import com.clody.domain.diary.dto.DiaryContent;
import java.util.List;

public record DiaryResponse(
    List<DiaryContent> diaries,
    boolean isDeleted
) {

  public static DiaryResponse of(List<DiaryContent> diaries, boolean isDeleted) {
    return new DiaryResponse(diaries, isDeleted);
  }
}

