package com.clody.clodyapi.diary.usecase;

import com.clody.domain.diary.dto.DiaryListGetResponse;
import com.clody.domain.diary.dto.response.DiaryCalenderGetResponse;

public interface DiaryRetrieverUsecase {

    DiaryListGetResponse retrieveListDiary(int year, int month);
    DiaryCalenderGetResponse retrieveCalendarDiary(int year, int month);
}
