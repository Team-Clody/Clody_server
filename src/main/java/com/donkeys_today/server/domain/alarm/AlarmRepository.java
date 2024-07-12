package com.donkeys_today.server.domain.alarm;

import com.donkeys_today.server.domain.user.User;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AlarmRepository {

  List<Alarm> findAllByTime(LocalTime time);

  Alarm save(Alarm alarm);

  Optional<Alarm> findByUser(User user);

}
