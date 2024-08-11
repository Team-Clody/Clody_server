package com.clody.domain.diary.service;

import com.clody.domain.diary.Diary;
import com.clody.domain.diary.ReplyStatus;
import com.clody.domain.diary.dto.DiaryContent;
import com.clody.domain.diary.dto.DiaryDeletionInfo;
import com.clody.domain.diary.dto.DiaryDomainInfo;
import com.clody.domain.diary.dto.DiaryFullInfo;
import com.clody.domain.diary.dto.DiaryListGetResponse;
import com.clody.domain.diary.dto.DiarySimpleInfo;
import com.clody.domain.diary.event.DiaryDeletionEvent;
import com.clody.domain.diary.event.publisher.DiaryEventPublisher;
import com.clody.domain.diary.event.UserDiaryWrittenEvent;
import com.clody.domain.diary.event.strategy.DiaryStrategyManager;
import com.clody.domain.diary.repository.DiaryRepository;
import com.clody.domain.reply.Reply;
import com.clody.domain.reply.ReplyType;
import com.clody.domain.reply.repository.ReplyRepository;
import com.clody.domain.user.User;
import com.clody.domain.user.event.UserEventPublisher;
import com.clody.domain.user.repository.UserRepository;
import com.clody.support.security.util.JwtUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryService {

  private final DiaryRepository diaryRepository;
  private final ReplyRepository replyRepository;
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


  public DiaryListGetResponse getDiaryList(int year, int month) {
    Map<LocalDate, List<Diary>> diariesByDate = getDiariesMap(year, month);
    Map<LocalDate, Reply> repliesByDate = getRepliesMap(year, month);
    List<DiaryFullInfo> diaryFullInfos = new ArrayList<>();

    for (LocalDate date : diariesByDate.keySet()) {
      List<Diary> foundDiaries = diariesByDate.get(date);
      int diaryCount = foundDiaries.size();
      ReplyStatus replyStatus = null;
      if (isReplyExist(date, repliesByDate) && isReplyRead(date, repliesByDate)) {
        replyStatus = ReplyStatus.READY_READ;
      } else if (isReplyExist(date, repliesByDate)) {
        replyStatus = ReplyStatus.READY_NOT_READ;
      } else {
        replyStatus = ReplyStatus.UNREADY;
      }
      List<DiaryContent> diaryContents = getDiaryContentList(foundDiaries);
      DiaryFullInfo diaryFullInfo = DiaryFullInfo.of(diaryCount, replyStatus, date, diaryContents, isDeleted(date));
      diaryFullInfos.add(diaryFullInfo);
    }

    diaryFullInfos.sort(Comparator.comparing(DiaryFullInfo::date));
    return DiaryListGetResponse.of(getCloverCount(year), diaryFullInfos);
  }

  private int getCloverCount(int year) {
    LocalDateTime start = LocalDateTime.of(year, 1, 1, 0, 0);
    LocalDateTime end = start.plusYears(1);
    return replyRepository.findByUserIdAndDiaryCreatedDateBetween(JwtUtil.getLoginMemberId(), start.toLocalDate(),
                    end.toLocalDate()).stream().filter(Reply::isRead)  // is_read가 true 인 것만 필터링
            .toList().size();
  }

  private Map<LocalDate, List<Diary>> getDiariesMap(int year, int month) {
    LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
    LocalDateTime end = start.plusMonths(1);
    List<Diary> diaries = diaryRepository.findDiariesByUserIdAndCreatedAtBetween(JwtUtil.getLoginMemberId(), start, end);
    List<Diary> filteredDiaries = diaries.stream()
            .filter(diary -> !diary.isDeleted())
            .toList();

    return filteredDiaries.stream()
            .collect(Collectors.groupingBy(diary -> diary.getCreatedAt().toLocalDate()));
  }

  private Map<LocalDate, Reply> getRepliesMap(int year, int month) {
    LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
    LocalDateTime end = start.plusMonths(1);
    List<Reply> replies = replyRepository.findByUserIdAndDiaryCreatedDateBetween(JwtUtil.getLoginMemberId(), start.toLocalDate(),
            end.toLocalDate());
    return replies.stream()
            .collect(Collectors.toMap(Reply::getDiaryCreatedDate, reply -> reply));
  }
  private Boolean isDeleted(LocalDate date) {
    LocalDateTime start = date.atStartOfDay();
    LocalDateTime end = start.plusDays(1);
    return diaryRepository.findDiariesByUserIdAndCreatedAtBetween(JwtUtil.getLoginMemberId(), start, end).stream().anyMatch(Diary::isDeleted);
  }

  private void setIsDeleted(int year, int month, List<DiarySimpleInfo> diarySimpleInfos) {
    YearMonth yearMonth = YearMonth.of(year, month);

    // 해당 월의 일 수를 가져오기
    int daysInMonth = yearMonth.lengthOfMonth();

    // 모든 날짜를 순회하는 for 문
    for (int day = 1; day <= daysInMonth; day++) {
      LocalDate date = yearMonth.atDay(day);
      if (isDeleted(date)) {
        DiarySimpleInfo diarySimpleInfo = DiarySimpleInfo.of(0, ReplyStatus.UNREADY, true);
        diarySimpleInfos.set(date.getDayOfMonth() - 1, diarySimpleInfo);
      }
    }
  }

  private boolean isReplyExist(LocalDate date, Map<LocalDate, Reply> repliesByDate) {
    return repliesByDate.containsKey(date);
  }

  private Boolean isReplyRead(LocalDate date, Map<LocalDate, Reply> repliesByDate) {
    return repliesByDate.get(date).getIs_read();
  }

  private List<DiaryContent> getDiaryContentList(List<Diary> foundDiaries) {
    return foundDiaries.stream()
            .map(diary -> new DiaryContent(diary.getContent()))
            .toList();
  }

}
