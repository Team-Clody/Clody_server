package com.donkeys_today.server.presentation.diary.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record DiaryCreatedResponse(
    String createdAt
) {
  public static DiaryCreatedResponse createLocalDateTimeToString(LocalDateTime createdAt) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return new DiaryCreatedResponse(createdAt.format(formatter));
  }
}
