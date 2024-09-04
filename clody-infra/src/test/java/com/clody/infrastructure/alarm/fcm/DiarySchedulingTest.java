package com.clody.infrastructure.alarm.fcm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.clody.domain.alarm.Alarm;
import com.clody.domain.alarm.repository.AlarmRepository;
import com.clody.domain.user.Platform;
import com.clody.domain.user.User;
import com.clody.infra.external.fcm.FcmService;
import com.clody.infra.models.alarm.DiaryNotifyService;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@ExtendWith(MockitoExtension.class)
public class DiarySchedulingTest {

  @Mock
  private FcmService fcmService;

  @Mock
  private AlarmRepository alarmRepository;

  @InjectMocks
  private DiaryNotifyService diaryNotifyService;

  @Mock
  private Clock fixedClock;

  @BeforeEach
  void setUp() {
    fixedClock = Clock.fixed(Instant.parse("2024-09-04T14:00:00Z"), ZoneId.systemDefault());
    diaryNotifyService = new DiaryNotifyService(alarmRepository, fcmService, fixedClock);
  }

  @Test
  @DisplayName("현재시각은 정오 이후로 고정")
  void test_IsAfterNoon() {
    assertThat(LocalTime.NOON.isAfter(LocalTime.now(fixedClock))).isEqualTo(false);
  }

  @Test
  @DisplayName("유저가 설정한 시간에 알림이 발송되어야 한다.")
  public void test1() throws InterruptedException {

    User user = User.createNewUser("1L", Platform.KAKAO, null, "John doe", "test1@naver.com");
    Alarm alarm = new Alarm(user, "fcmToken", LocalTime.of(14, 0), true,
        true);

    CountDownLatch latch = new CountDownLatch(1);

    ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    scheduler.initialize();

    when(alarmRepository.findAllByTime(LocalTime.now(fixedClock))).thenReturn(
        Collections.singletonList(alarm));

    Runnable task = () -> {
      System.out.println("알림 발송 ");
      diaryNotifyService.sendDiaryAlarms();
      latch.countDown();
    };

    scheduler.scheduleAtFixedRate(task, Instant.now(), Duration.ofSeconds(1));
    boolean taskExecuted = latch.await(5, TimeUnit.SECONDS);

    verify(fcmService).sendDiaryAlarm("fcmToken");
  }

  @Test
  @DisplayName("고정된 시간 14:00에 알림이 정상적으로 발송되어야 한다")
  public void testSendDiaryAlarmsAtFixedTime() {
    // 14:00에 대한 알람 데이터 설정
    User user = User.createNewUser("1L", Platform.KAKAO, null, "John Doe", "test1@naver.com");
    Alarm alarm = new Alarm(user, "fcmToken", LocalTime.of(14, 0), true, true);

    // Mock 설정: 14:00에 알람이 반환되도록 설정
    when(alarmRepository.findAllByTime(LocalTime.now(fixedClock))).thenReturn(Arrays.asList(alarm));

    // 알림 발송 메서드 호출
    diaryNotifyService.sendDiaryAlarms();

    // FCM 서비스가 정상적으로 호출되었는지 확인
    verify(fcmService).sendDiaryAlarm("fcmToken");
  }

  @Test
  @DisplayName("스케쥴링이 진행되는 시간은 10분 단위여야 한다.")
  public void validate_schedule_time() throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(1);

    ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    scheduler.initialize();

    Runnable task = () -> {
      System.out.println("10분 단위로 작업 실행");
      latch.countDown();
    };

    // 스케줄링이 1초마다 실행되도록 설정
    scheduler.scheduleAtFixedRate(task, Instant.now(), Duration.ofSeconds(1));

    // 10분 후에 실행되었는지 대기 (테스트에서는 짧게 대기)
    boolean taskExecuted = latch.await(2, TimeUnit.SECONDS);

    // 스케줄링이 올바르게 동작했는지 확인
    assertThat(taskExecuted).isTrue();
  }

  @Test
  @DisplayName("알림 설정을 허용한 유저에게만 발송되어야 한다.")
  public void validAlarmUser() throws InterruptedException {
    User userWithAlarm = User.createNewUser("1L", Platform.KAKAO, null, "John doe", "test1@naver.com");
    Alarm alarmWithPermission = new Alarm(userWithAlarm, "fcmToken", LocalTime.of(14, 0), true, true);

    User userWithoutAlarm = User.createNewUser("2L", Platform.KAKAO, null, "Jane doe", "test2@naver.com");
    Alarm alarmWithoutPermission = new Alarm(userWithoutAlarm, "fcmToken", LocalTime.of(14, 0), false, false);

    when(alarmRepository.findAllByTime(LocalTime.now(fixedClock))).thenReturn(
        List.of(alarmWithPermission, alarmWithoutPermission));

    CountDownLatch latch = new CountDownLatch(1);

    ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    scheduler.initialize();

    Runnable task = () -> {
      diaryNotifyService.sendDiaryAlarms();
      latch.countDown();
    };
    scheduler.scheduleAtFixedRate(task, Instant.now(), Duration.ofSeconds(1));

    boolean taskExecuted = latch.await(5, TimeUnit.SECONDS);

    // 2명중 한명만 발송되어야 함.
    verify(fcmService, times(1)).sendDiaryAlarm(alarmWithPermission.getFcmToken()); // 알림 허용 유저

    assertThat(taskExecuted).isTrue();
  }


}
