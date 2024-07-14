package com.donkeys_today.server.application.diary.dto;

public record DiaryMessage(
    Long userId,
    String message,
    String date
) {

  public static DiaryMessage of(Long userId, String message, String date) {
    return new DiaryMessage(userId, message, date);
  }
}
