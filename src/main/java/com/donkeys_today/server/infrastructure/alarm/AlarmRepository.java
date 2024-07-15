package com.donkeys_today.server.infrastructure.alarm;

import com.donkeys_today.server.domain.alarm.Alarm;
import com.donkeys_today.server.domain.user.User;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

  Optional<Alarm> findByUser(User user);

  List<Alarm> findAllByTime(LocalTime time);
}
