package com.clody.clodyapi.diary.service;

import com.clody.domain.diary.dto.DiaryListGetResponse;
import com.clody.clodyapi.diary.usecase.DiaryRetrieverUsecase;
import com.clody.domain.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryRetrieverService implements DiaryRetrieverUsecase {

    private final DiaryService diaryService;

    @Override
    public DiaryListGetResponse retrieveListDiary(int year, int month) {
        return diaryService.getDiaryList(year, month);
    }
}