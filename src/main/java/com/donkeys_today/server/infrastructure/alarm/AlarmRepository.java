package com.donkeys_today.server.infrastructure.alarm;

import com.donkeys_today.server.domain.alarm.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

}
