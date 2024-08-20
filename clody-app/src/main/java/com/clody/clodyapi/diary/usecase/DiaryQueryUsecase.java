package com.clody.clodyapi.diary.usecase;

import com.clody.clodyapi.diary.controller.dto.response.DiaryCreatedTimeResponse;

public interface DiaryQueryUsecase {

  DiaryCreatedTimeResponse getCreatedTime(final int year, final int month, final int date);
}
