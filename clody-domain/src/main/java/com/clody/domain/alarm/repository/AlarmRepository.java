package com.clody.domain.alarm.repository;

import com.clody.domain.alarm.Alarm;
import com.clody.domain.user.User;
import java.time.LocalTime;
import java.util.List;

public interface AlarmRepository {

  Alarm findByUser(User user);

  List<Alarm> findAllByTime(LocalTime localTime);

  Alarm save(Alarm alarm);
}
