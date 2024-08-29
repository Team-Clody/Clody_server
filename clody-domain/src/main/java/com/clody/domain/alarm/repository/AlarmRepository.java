package com.clody.domain.alarm.repository;

import com.clody.domain.alarm.Alarm;
import com.clody.domain.user.User;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AlarmRepository {

  Alarm findByUser(User user);

  Alarm findByUserId(Long userId);

  List<Alarm> findAllByTime(LocalTime localTime);

  Optional<Alarm> findUserAgreedForReply(Long userId);

  Alarm save(Alarm alarm);
}
