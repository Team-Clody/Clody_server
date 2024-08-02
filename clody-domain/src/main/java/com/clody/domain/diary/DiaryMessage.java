package com.clody.domain.diary;

public record DiaryMessage(
    Long userId,
    String message,
    String date
) {

  public static DiaryMessage of(Long userId, String message, String date) {
    return new DiaryMessage(userId, message, date);
  }
}
