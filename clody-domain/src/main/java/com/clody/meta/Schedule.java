package com.clody.meta;

import com.clody.domain.reply.ReplyType;
import com.clody.support.dto.type.ErrorType;
import com.clody.support.exception.BusinessException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "schedules")
public class Schedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long userId;

  private LocalDateTime notificationTime;

  private boolean notificationSent;

  @Builder
  public Schedule(Long userId, LocalDateTime notificationTime) {
    this.userId = userId;
    this.notificationTime = notificationTime;
  }

  @Builder
  public static Schedule create(Long userId, LocalDateTime replyCreationTime, ReplyType type) {
    LocalDateTime notificationTime = calculateNotificationTime(type);
    return new Schedule(userId, notificationTime);
  }

  private static LocalDateTime calculateNotificationTime(ReplyType type) {
    switch (type) {
      case FIRST:
        return LocalDateTime.now().plusMinutes(10);
      case STATIC:
        return LocalDateTime.now().plusHours(5);
      case DYNAMIC:
        return LocalDateTime.now().plusHours(10);
      default:
        throw new BusinessException(ErrorType.INVALID_REPLY_TYPE);
    }
  }
}
