package com.clody.clodyapi.diary.mapper;

import com.clody.clodyapi.diary.controller.dto.response.DiaryCreatedResponse;
import com.clody.clodyapi.diary.controller.dto.response.DiaryCreatedTimeResponse;
import com.clody.domain.diary.dto.DiaryDateInfo;
import com.clody.domain.diary.dto.DiaryDeletionInfo;
import com.clody.domain.diary.dto.DiaryDomainInfo;
import com.clody.domain.reply.ReplyType;
import com.clody.support.security.util.JwtUtil;
import java.time.LocalDateTime;

public class DiaryMapper {

  public static DiaryCreatedResponse toDiaryResponse(DiaryDomainInfo domainInfo) {
    ReplyType type = domainInfo.replyType();
    LocalDateTime createdAt = domainInfo.diaryList().getFirst().getCreatedAt();
    return DiaryCreatedResponse.of(createdAt, type);
  }

  public static DiaryDeletionInfo toDiaryDeletionInfo(int year, int month, int date) {
    Long userId = JwtUtil.getLoginMemberId();
    return DiaryDeletionInfo.of(userId, year, month, date);
  }

  public static DiaryDateInfo toDiaryDateInfo(int year, int month, int date) {
    return DiaryDateInfo.of(year, month, date);
  }

  public static DiaryCreatedTimeResponse toDiaryCreatedTimeResponse(int hour, int minute,
      int second) {
    return DiaryCreatedTimeResponse.of(hour, minute, second);
  }

}
