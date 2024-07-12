package com.donkeys_today.server.application.alarm;

import com.donkeys_today.server.application.auth.JwtUtil;
import com.donkeys_today.server.application.user.UserService;
import com.donkeys_today.server.domain.alarm.Alarm;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.infrastructure.alarm.AlarmJpaRepository;
import com.donkeys_today.server.infrastructure.refreshToken.redis.RedisConstants;
import com.donkeys_today.server.presentation.alarm.dto.request.AlarmRequest;
import com.donkeys_today.server.presentation.alarm.dto.response.AlarmResponse;
import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.BusinessException;
import com.donkeys_today.server.support.exception.NotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

//public Alarm createAlarmIfNotExist() {
//  return Alarm.builder()
//      .fcmToken()
//      .user()
//      .isReplyAlarm()
//      .isDiaryAlarm()
//}
//
//@Scheduled(cron = "0 */10 * * * *") // 매 10분마다 실행
//public void sendDiaryRequestNotifications() {
//
//  LocalTime currentTime = LocalTime.now().withSecond(0).withNano(0);
//
//  Set<String> keys = redisTemplate.keys(RedisConstants.DIARY_ALARM_PREFIX + "*");
//  Set<String> targetUsers = findTargetUsers(currentTime, keys);
//
//  for (String targetUser : targetUsers) {
//    String fcmToken = extractFcmToken(targetUser);
//    fcmService.sendDiaryAlarm(fcmToken, MessageContent.DIARY_WRITE_REQUEST);
//  }
//  //반복 돌며 전송;
//}
//
//private Set<String> findTargetUsers(LocalTime currentTime, Set<String> keys) {
//  Set<String> targetUsers = new HashSet<>();
//  for (String key : keys) {
//    if (redisTemplate.opsForValue().get(key).equals(getFormattedTimeString(currentTime))) {
//      targetUsers.add(key);
//    }
//  }
//  return targetUsers;
//}
//
//private String getFormattedTimeString(LocalTime time) {
//  return time.format(TIME_FORMATTER);
//}
//
//private void checkAlarmRegistered(String key, String value) {
//  String foundValue = redisTemplate.opsForValue().get(key);
//  if (foundValue == null || !foundValue.equals(value)) {
//    throw new BusinessException(ErrorType.FAIL_DIARY_ALARM_REGISTER);
//  }
//}

@Service
@RequiredArgsConstructor
public class AlarmService {

  private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
  private final LocalTime DEFAULT_ALARM_TIME = LocalTime.of(21, 0);

  private final RedisTemplate<String, String> redisTemplate;
  private final AlarmJpaRepository alarmJpaRepository;
  private final UserService userService;

  private static String extractFcmToken(String key) {
    if (key != null && key.startsWith(RedisConstants.DIARY_ALARM_PREFIX)) {
      return key.substring(RedisConstants.DIARY_ALARM_PREFIX.length());
    } else {
      throw new IllegalArgumentException("Invalid key format: " + key);
    }
  }

  public static LocalTime parseLocalTime(String timeString) {
    if (timeString == null || timeString.isEmpty()) {
      return null;
    }
    try {
      return LocalTime.parse(timeString, TIME_FORMATTER);
    } catch (DateTimeParseException e) {
      throw new BusinessException(ErrorType.INVALID_TIME_FORMAT);
    }
  }

  @Transactional
  public AlarmResponse updateAlarm(AlarmRequest request) {

    Alarm alarm = findAlarmByUser(
        userService.findUserById(JwtUtil.getLoginMemberId()));
    if (request.isDiaryAlarm() != null) {
      updateDiaryAlarmWithTime(alarm, request.isDiaryAlarm());
    }
    if (request.fcmToken() != null) {
      updateFcmToken(alarm, request.fcmToken());
    }
    if (request.isReplyAlarm() != null) {
      updateReplyAlarm(alarm, request.isReplyAlarm());
    }
    if (request.time() != null) {
      updateAlarmTime(alarm, request.time());
    }

    return AlarmResponse.of(alarm.getFcmToken(), alarm.getTime().toString(), alarm.isDiaryAlarm(),
        alarm.isReplyAlarm());
  }

  public Alarm updateAlarmTime(Alarm alarm, String time) {
    alarm.updateTime(parseLocalTime(time));
    return alarm;
  }

  public Alarm updateReplyAlarm(Alarm alarm, boolean isReplyAlarm) {
    alarm.updateReplyAlarm(isReplyAlarm);
    return alarm;
  }

  public Alarm updateFcmToken(Alarm alarm, String fcmToken) {
    if (fcmToken != null) {
      alarm.updateFcmToken(fcmToken);
    }
    return alarm;
  }

  public Alarm updateDiaryAlarmWithTime(Alarm alarm, boolean isDiaryAlarm) {
    // 알림을 켜고, 기본시간으로 설정한다.
    if (isDiaryAlarm) {
      alarm.updateDiaryAlarm(isDiaryAlarm);
      alarm.updateTime(DEFAULT_ALARM_TIME);
    } else {
      alarm.updateDiaryAlarm(isDiaryAlarm);
    }
    return alarm;
  }

  public Alarm findAlarmByUser(User user) {
    return alarmJpaRepository.findByUser(user).orElseThrow(
        () -> new NotFoundException(ErrorType.USER_NOT_FOUND)
    );
  }

  public List<Alarm> findAlarmsByCurrentTime(LocalTime currentTime) {
    return alarmJpaRepository.findAllByTime(currentTime);
  }

  public AlarmResponse getUserAlarm() {
    User user = userService.findUserById(JwtUtil.getLoginMemberId());
    Alarm alarm = findAlarmByUser(user);
    return AlarmResponse.of(alarm.getFcmToken(), alarm.getTime().toString(), alarm.isDiaryAlarm(),
        alarm.isReplyAlarm());
  }
}
