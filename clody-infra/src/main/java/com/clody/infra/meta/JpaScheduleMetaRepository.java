package com.clody.infra.meta;

import com.clody.meta.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaScheduleMetaRepository extends JpaRepository<Schedule, Long> {

}
