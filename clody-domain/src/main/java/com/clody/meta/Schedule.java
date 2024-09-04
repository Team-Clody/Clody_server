package com.clody.meta;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalTime;
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

  private LocalTime notificationTime;

  private boolean notificationSent;

  @Builder
  public Schedule(Long userId, LocalTime notificationTime) {
    this.userId = userId;
    this.notificationTime = notificationTime;
  }

  @Builder
  public static Schedule create(Long userId, LocalTime userRequestedTime) {
    return new Schedule(userId, userRequestedTime);
  }

  public void notifySent() {
    this.notificationSent = true;
  }
}
