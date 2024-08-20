package com.clody.clodyapi.diary.usecase;

import com.clody.domain.diary.dto.DiaryListGetResponse;

public interface DiaryRetrieverUsecase {

    DiaryListGetResponse retrieveListDiary(int year, int month);
}
