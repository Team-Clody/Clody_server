package com.clody.domain.diary.dto;

import java.time.LocalDateTime;

public record DiaryDateInfo(
    int year,
    int month,
    int date
) {

  public static DiaryDateInfo of(int year, int month, int date) {
    return new DiaryDateInfo(year, month, date);
  }

  public LocalDateTime parseToLocalDateTime() {
    return LocalDateTime.of(year, month, date, 0, 0);
  }
}
