package com.clody.infra.config;

import com.clody.domain.alarm.repository.AlarmRepository;
import com.clody.infra.models.alarm.JpaAlarmRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  public AlarmRepository alarmRepository(JpaAlarmRepository jpaAlarmRepository) {
    return jpaAlarmRepository;
  }
}
