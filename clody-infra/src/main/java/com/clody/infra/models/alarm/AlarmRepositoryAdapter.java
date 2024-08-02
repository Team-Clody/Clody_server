package com.clody.infra.models.alarm;

import com.clody.domain.alarm.Alarm;
import com.clody.domain.alarm.repository.AlarmRepository;
import com.clody.domain.user.User;
import java.time.LocalTime;
import java.util.List;
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
  public List<Alarm> findAllByTime(LocalTime localTime) {
    return alarmRepository.findAllByTime(localTime);
  }

  @Override
  public Alarm save(Alarm alarm) {
    return alarmRepository.save(alarm);
  }
}
