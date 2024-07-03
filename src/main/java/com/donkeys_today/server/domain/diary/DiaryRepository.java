package com.donkeys_today.server.domain.diary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

}
