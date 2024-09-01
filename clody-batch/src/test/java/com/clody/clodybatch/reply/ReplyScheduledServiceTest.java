package com.clody.clodybatch.reply;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.clody.clodybatch.service.ReplyScheduleService;
import com.clody.domain.reply.ReplyType;
import com.clody.meta.Schedule;
import com.clody.meta.repository.ScheduleMetaRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReplyScheduledServiceTest {

  @Mock
  private ScheduleMetaRepository scheduleMetaRepository;

  @InjectMocks
  private ReplyScheduleService replyScheduleService;


  @Test
  public void 스케줄_알림_발송할_대상_탐색() {

    //given
    LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    Long userId = 1L;
    LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
    ReplyType replyType = ReplyType.DYNAMIC;

    Schedule schedule = Schedule.create(userId, yesterday, replyType);

    //when
    when(scheduleMetaRepository.findSchedulesToNotify(now)).thenReturn(
        Collections.singletonList(schedule));

    //then
    List<Schedule> result = replyScheduleService.findSchedulesToNotify(now);
    assertEquals(1, result.size());
    assertEquals(schedule, result.getFirst());
  }
}
