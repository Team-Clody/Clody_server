package com.clody.infra.models.alarm;

import com.clody.domain.alarm.Alarm;
import com.clody.domain.alarm.repository.AlarmRepository;
import com.clody.domain.user.User;
import com.clody.support.dto.type.ErrorType;
import com.clody.support.exception.NotFoundException;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class AlarmRepositoryAdapter implements AlarmRepository {

  private final JpaAlarmRepository alarmRepository;

  public AlarmRepositoryAdapter(JpaAlarmRepository alarmRepository) {
    this.alarmRepository = alarmRepository;
  }

  @Override
  public Alarm findByUser(User user) {
    return alarmRepository.findByUser(user);
  }

  @Override
  public Alarm findByUserId(Long userId) {
    return alarmRepository.findByUserId(userId).orElseThrow(
        () -> new NotFoundException(ErrorType.ALARM_NOT_FOUND)
    );
  }

  @Override
  public List<Alarm> findAllByTime(LocalTime localTime) {
    return alarmRepository.findAllByTime(localTime);
  }

  @Override
  public Alarm save(Alarm alarm) {
    return alarmRepository.save(alarm);
  }

  @Override
  public Optional<Alarm> findUserAgreedForReply(Long userId) {
    return alarmRepository.findByUserIdAndReplyAlarmIsTrue(userId);
  }
}
