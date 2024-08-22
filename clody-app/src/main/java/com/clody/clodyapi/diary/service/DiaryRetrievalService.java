package com.clody.clodyapi.diary.service;

import com.clody.clodyapi.diary.controller.dto.response.DiaryCreatedTimeResponse;
import com.clody.clodyapi.diary.controller.dto.response.DiaryResponse;
import com.clody.clodyapi.diary.mapper.DiaryMapper;
import com.clody.clodyapi.diary.usecase.DiaryQueryUsecase;
import com.clody.domain.diary.dto.DiaryContent;
import com.clody.domain.diary.dto.DiaryDateInfo;
import com.clody.domain.diary.dto.response.DiaryTimeInfo;
import com.clody.domain.diary.service.DiaryQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryRetrievalService implements DiaryQueryUsecase {

  private final DiaryQueryService diaryQueryService;

  @Override
  public DiaryCreatedTimeResponse getCreatedTime(int year, int month, int date) {
    DiaryDateInfo diaryDateInfo = DiaryMapper.toDiaryDateInfo(year, month, date);
    DiaryTimeInfo diaryTimeInfo = diaryQueryService.getCreatedTime(diaryDateInfo);

    return DiaryMapper.toDiaryCreatedTimeResponse(diaryTimeInfo.HH(), diaryTimeInfo.MM(),
        diaryTimeInfo.SS());
  }

  @Override
  public DiaryResponse getDiary(final int year,final int month,final int date) {
    DiaryDateInfo diaryDateInfo = DiaryMapper.toDiaryDateInfo(year, month, date);
    List<DiaryContent> diaryContentList = diaryQueryService.getDiary(diaryDateInfo);
    return DiaryMapper.toDiaryResponse(diaryContentList);
  }
}
