package com.clody.clodyapi.diary.usecase;

import com.clody.clodyapi.diary.controller.dto.response.DiaryResponse;

public interface DiaryDeletionUsecase {

  DiaryResponse deleteDiary(final int year, final int month, final int date);

}
