package com.donkeys_today.server.presentation.diary.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record DiaryResponse(
    String createdAt
) {

  public static DiaryResponse createLocalDateTimeToString(LocalDateTime createdAt) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return new DiaryResponse(createdAt.format(formatter));
  }

}
