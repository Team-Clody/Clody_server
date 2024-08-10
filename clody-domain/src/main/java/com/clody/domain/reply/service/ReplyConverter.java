package com.clody.domain.reply.service;

import com.clody.domain.diary.Diary;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReplyConverter {

  public static String convertDiaryContentToReplyRequestForm(List<Diary> diaryList){
    return IntStream.range(0, diaryList.size())
        .mapToObj(i -> (i + 1) + ". " + diaryList.get(i).getContent())
        .collect(Collectors.joining(", "));
  }

}
