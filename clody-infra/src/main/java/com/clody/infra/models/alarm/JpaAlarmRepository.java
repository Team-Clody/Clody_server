package com.clody.infra.models.alarm;

import com.clody.domain.alarm.Alarm;
import com.clody.domain.alarm.repository.AlarmRepository;
import com.clody.domain.user.User;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAlarmRepository extends JpaRepository<Alarm, Long>, AlarmRepository {

  Alarm findByUser(User user);

  List<Alarm> findAllByTime(LocalTime time);

  Alarm save(Alarm alarm);
}
