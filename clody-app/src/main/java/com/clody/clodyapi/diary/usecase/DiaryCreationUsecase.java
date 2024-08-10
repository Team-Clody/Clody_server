package com.clody.clodyapi.diary.usecase;

import com.clody.clodyapi.diary.controller.dto.request.DiaryRequest;
import com.clody.clodyapi.diary.controller.dto.response.DiaryCreatedResponse;

public interface DiaryCreationUsecase {

  DiaryCreatedResponse createDiary(DiaryRequest diaryRequest);

}
