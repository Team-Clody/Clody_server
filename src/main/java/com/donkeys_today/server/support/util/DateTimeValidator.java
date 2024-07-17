package com.donkeys_today.server.support.util;


import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.InvalidDateFormatException;

public class DateTimeValidator {

  public static final int MIN_YEAR = 1;
  public static final int MAX_YEAR = 2030;
  public static final int MIN_MONTH = 1;
  public static final int MAX_MONTH = 12;
  public static final int MIN_DATE = 1;
  public static final int MAX_DATE = 31;
  
  public static void validateLocalDateTime(int year, int month, int date) {
    if (year < MIN_YEAR || year > MAX_YEAR || month < MIN_MONTH || month > MAX_MONTH || date < MIN_DATE || date > MAX_DATE) {
      throw new InvalidDateFormatException(ErrorType.INVALID_DATE_FORMAT);
    }
  }

  public static void validateLocalDate(int year, int month) {
    if (year < MIN_YEAR || year > MAX_YEAR || month < MIN_MONTH || month > MAX_MONTH) {
      throw new InvalidDateFormatException(ErrorType.INVALID_DATE_FORMAT);
    }
  }
}
