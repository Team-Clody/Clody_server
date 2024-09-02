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
import java.time.temporal.ChronoUnit;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
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

    LocalDateTime notificationTime = calculateNotificationTime(replyCreationTime, type);
    return new Schedule(userId, notificationTime);
  }

  public void notifySent() {
    this.notificationSent = true;
  }

  private static LocalDateTime calculateNotificationTime(LocalDateTime replyCreationTime, ReplyType type) {
    replyCreationTime = replyCreationTime.truncatedTo(ChronoUnit.SECONDS);
    return switch (type) {
      case FIRST -> replyCreationTime.plusMinutes(10);
      case STATIC -> replyCreationTime.plusHours(5);
      case DYNAMIC -> replyCreationTime.plusHours(10);
      default -> throw new BusinessException(ErrorType.INVALID_REPLY_TYPE);
    };
  }

  public static Schedule truncateSeconds(Schedule schedule){
    return new Schedule(schedule.getUserId(), schedule.getNotificationTime().truncatedTo(ChronoUnit.SECONDS));
  }
}
