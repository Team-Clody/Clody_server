package com.clody.clodyapi.diary.service;

import com.clody.clodyapi.diary.controller.dto.request.DiaryRequest;
import com.clody.clodyapi.diary.controller.dto.response.DiaryCreatedResponse;
import com.clody.clodyapi.diary.mapper.DiaryMapper;
import com.clody.clodyapi.diary.usecase.DiaryCreationUsecase;
import com.clody.domain.diary.dto.DiaryDomainInfo;
import com.clody.domain.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryCreationService implements DiaryCreationUsecase {

  private final DiaryService diaryService;
  @Override
  public DiaryCreatedResponse createDiary(DiaryRequest request) {
    DiaryDomainInfo diaryDomainInfo = diaryService.createDiary(request.content());
    return DiaryMapper.toDiaryResponse(diaryDomainInfo);
  }
}
