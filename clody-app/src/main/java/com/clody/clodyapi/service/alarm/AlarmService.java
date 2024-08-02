package com.clody.clodyapi.service.alarm;

import com.clody.clodyapi.presentation.alarm.dto.request.AlarmRequest;
import com.clody.clodyapi.presentation.alarm.dto.response.AlarmResponse;
import com.clody.clodyapi.service.user.UserService;
import com.clody.support.dto.type.ErrorType;
import com.clody.support.exception.BusinessException;
import com.clody.domain.alarm.Alarm;
import com.clody.domain.alarm.repository.AlarmRepository;
import com.clody.domain.user.User;
import com.clody.support.security.util.JwtUtil;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  private final AlarmRepository alarmRepository;
  private final UserService userService;

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

    Alarm alarm =alarmRepository.findByUser(userService.getUserById(JwtUtil.getLoginMemberId()));

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

  public List<Alarm> findAlarmsByCurrentTime(LocalTime currentTime) {
    return alarmRepository.findAllByTime(currentTime);
  }

  public AlarmResponse getUserAlarm() {
    User user = userService.getUserById(JwtUtil.getLoginMemberId());
    Alarm alarm = alarmRepository.findByUser(user);
    return AlarmResponse.of(alarm.getFcmToken(), alarm.getTime().toString(), alarm.isDiaryAlarm(),
        alarm.isReplyAlarm());
  }
}
