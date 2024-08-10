package com.clody.domain.diary.service;

import com.clody.domain.diary.Diary;
import com.clody.domain.diary.dto.DiaryDeletionInfo;
import com.clody.domain.diary.dto.DiaryDomainInfo;
import com.clody.domain.diary.event.DiaryDeletionEvent;
import com.clody.domain.diary.event.publisher.DiaryEventPublisher;
import com.clody.domain.diary.event.UserDiaryWrittenEvent;
import com.clody.domain.diary.event.strategy.DiaryStrategyManager;
import com.clody.domain.diary.repository.DiaryRepository;
import com.clody.domain.reply.ReplyType;
import com.clody.domain.user.User;
import com.clody.domain.user.event.UserEventPublisher;
import com.clody.domain.user.repository.UserRepository;
import com.clody.support.security.util.JwtUtil;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryService {

  private final DiaryRepository diaryRepository;
  private final UserRepository userRepository;

  private final UserEventPublisher userStatusProcessor;
  private final DiaryEventPublisher diaryEventProcessor;

  private final DiaryStrategyManager strategyManager;

  public DiaryDomainInfo createDiary(List<String> diaryContents) {
    Long userId = JwtUtil.getLoginMemberId();
    User user = userRepository.findById(userId);

    List<Diary> diaryList = Diary.createDiaryList(user, diaryContents);
    List<Diary> savedDiaryList = diaryRepository.saveAll(diaryList);

    //각 event 프로세서에 이벤트 전파
    diaryEventProcessor.publish(savedDiaryList);
    ReplyType replyType = strategyManager.determineReplyType(savedDiaryList);
    userStatusProcessor.publishUserStatusChangedEvent(new UserDiaryWrittenEvent(user));

    return DiaryDomainInfo.of(savedDiaryList, replyType);
  }

  public void removeDiarySoft(DiaryDeletionInfo info) {
    LocalDate targetDate = LocalDate.of(info.year(), info.month(), info.date());
    List<Diary> diaryList = diaryRepository.findDiaryByUserId(info.userId());

    diaryList.stream().filter(diary -> diary.matches(targetDate, info.userId()))
        .forEach(Diary::deleteDiary);

    diaryRepository.saveAll(diaryList);
    diaryEventProcessor.raiseDeletion(new DiaryDeletionEvent(info.userId(), targetDate));
  }
}
