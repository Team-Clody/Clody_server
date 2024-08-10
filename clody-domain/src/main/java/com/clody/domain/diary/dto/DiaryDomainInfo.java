package com.clody.domain.diary.dto;

import com.clody.domain.diary.Diary;
import com.clody.domain.reply.ReplyType;
import java.util.List;

public record DiaryDomainInfo(
    List<Diary> diaryList,
    ReplyType replyType
) {

  public static DiaryDomainInfo of(List<Diary> diaryList, ReplyType replyType) {
    return new DiaryDomainInfo(diaryList, replyType);
  }
}
