  package com.clody.clodyapi.diary.mapper;

  import com.clody.clodyapi.diary.controller.dto.response.DiaryCreatedResponse;
  import com.clody.clodyapi.diary.controller.dto.response.DiaryCreatedTimeResponse;
  import com.clody.clodyapi.diary.controller.dto.response.DiaryResponse;
  import com.clody.domain.diary.dto.DiaryContent;
  import com.clody.domain.diary.dto.DiaryDateInfo;
  import com.clody.domain.diary.dto.DiaryDeletionInfo;
  import com.clody.domain.diary.dto.DiaryDomainInfo;
  import com.clody.domain.diary.dto.response.DiaryListGetResponse;
  import com.clody.domain.diary.dto.response.DiaryCalenderGetResponse;
  import com.clody.domain.diary.dto.response.DiaryCreatedInfo;
  import com.clody.domain.diary.dto.response.DiaryDayInfo;
  import com.clody.domain.diary.dto.response.DiaryListInfo;
  import com.clody.domain.reply.ReplyType;
  import com.clody.support.security.util.JwtUtil;
  import java.time.LocalDateTime;
  import java.util.List;

  public class DiaryMapper {

    public static DiaryResponse toDiaryResponse(List<DiaryContent> diaryContentList,
        boolean userHasDeletedDiary) {
      return DiaryResponse.of(diaryContentList, userHasDeletedDiary);
    }

    public static DiaryCreatedResponse toDiaryCreatedResponse(DiaryDomainInfo domainInfo) {
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

    public static DiaryCreatedTimeResponse toDiaryCreatedTimeResponse(DiaryCreatedInfo info) {
      return DiaryCreatedTimeResponse.of(info.HH(), info.MM(), info.SS(), info.replyType());
    }

    public static DiaryListGetResponse toDiaryListResponse(DiaryListInfo diaryListInfo) {

      int totalCloverCount = diaryListInfo.totalCloverCount();
      List<DiaryDayInfo> diaries = diaryListInfo.diaries();
      return DiaryListGetResponse.of(totalCloverCount, diaries);
    }

    public static DiaryCalenderGetResponse toDiaryCalendarResponse(DiaryListInfo diaryListInfo) {

      int totalCloverCount = diaryListInfo.totalCloverCount();
      List<DiaryDayInfo> diaries = diaryListInfo.diaries();
      return DiaryCalenderGetResponse.of(totalCloverCount, diaries);
    }

  }
