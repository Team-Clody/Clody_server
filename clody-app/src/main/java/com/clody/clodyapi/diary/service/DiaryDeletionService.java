package com.clody.clodyapi.diary.service;

import com.clody.clodyapi.diary.controller.dto.response.DiaryResponse;
import com.clody.clodyapi.diary.mapper.DiaryMapper;
import com.clody.clodyapi.diary.usecase.DiaryDeletionUsecase;
import com.clody.domain.diary.dto.DiaryDeletionInfo;
import com.clody.domain.diary.service.DiaryCommandService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryDeletionService implements DiaryDeletionUsecase {

  private final DiaryCommandService diaryCommandService;

  public DiaryResponse deleteDiary(final int year, final int month, final int date) {
    //Mapper에서 변환
    DiaryDeletionInfo diaryDeletionInfo = DiaryMapper.toDiaryDeletionInfo(year, month, date);
    diaryCommandService.removeDiarySoft(diaryDeletionInfo);
    return DiaryResponse.of(List.of());

  }
}
