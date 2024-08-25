package com.clody.clodyapi.diary.service;

import com.clody.clodyapi.diary.mapper.DiaryMapper;
import com.clody.domain.diary.dto.DiaryListGetResponse;
import com.clody.clodyapi.diary.usecase.DiaryRetrieverUsecase;
import com.clody.domain.diary.dto.response.DiaryCalenderGetResponse;
import com.clody.domain.diary.dto.response.DiaryListInfo;
import com.clody.domain.diary.service.DiaryCommandService;
import com.clody.domain.diary.service.DiaryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryRetrieverService implements DiaryRetrieverUsecase {

    private final DiaryQueryService diaryService;

    @Override
    public DiaryListGetResponse retrieveListDiary(int year, int month) {

        DiaryListInfo diaryListInfo = diaryService.getDiaryList(year, month);
        return DiaryMapper.toDiaryListResponse(diaryListInfo);
    }

    @Override
    public DiaryCalenderGetResponse retrieveCalendarDiary(int year, int month) {
        DiaryListInfo diaryListInfo = diaryService.getDiaryCalendar(year, month);
        return DiaryMapper.toDiaryCalendarResponse(diaryListInfo);
    }
}
