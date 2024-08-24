package com.clody.domain.diary.service;

import com.clody.domain.diary.Diary;
import com.clody.domain.diary.dto.DiaryContent;
import com.clody.domain.diary.dto.DiaryDeletionInfo;
import com.clody.domain.diary.dto.DiaryDomainInfo;
import com.clody.domain.diary.dto.DiaryFullInfo;
import com.clody.domain.diary.dto.DiaryListGetResponse;
import com.clody.domain.diary.dto.DiarySimpleInfo;
import com.clody.domain.diary.dto.response.DiaryCalenderGetResponse;
import com.clody.domain.diary.dto.response.DiaryDayInfo;
import com.clody.domain.diary.dto.response.DiaryListInfo;
import com.clody.domain.diary.event.DiaryDeletionEvent;
import com.clody.domain.diary.event.publisher.DiaryEventPublisher;
import com.clody.domain.diary.event.UserDiaryWrittenEvent;
import com.clody.domain.diary.event.strategy.DiaryStrategyManager;
import com.clody.domain.diary.repository.DiaryRepository;
import com.clody.domain.reply.Reply;
import com.clody.domain.reply.ReplyType;
import com.clody.domain.reply.UserReplyReadStatus;
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
public class DiaryCommandService {

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


  public DiaryListInfo getDiaryList(int year, int month) {
    LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
    LocalDateTime end = start.plusMonths(1);


    List<Diary> diaries = diaryRepository.findDiariesByUserIdAndCreatedAtBetween(JwtUtil.getLoginMemberId(), start, end);
    List<Reply> replies = replyRepository.findByUserIdAndDiaryCreatedDateBetween(JwtUtil.getLoginMemberId(), start.toLocalDate(), end.toLocalDate());

    List<DiaryDayInfo> diaryDayInfos = new ArrayList<>();

    Map<LocalDate, List<Diary>> diariesMapByDate = diaries.stream().collect(Collectors.groupingBy(diary -> diary.getCreatedAt().toLocalDate()));
    Map<LocalDate, Reply> repliesMapByDate = replies.stream().collect(Collectors.toMap(Reply::getDiaryCreatedDate, reply -> reply));
    UserReplyReadStatus replyStatus = null;

    for (LocalDate date : diariesMapByDate.keySet()) {

      List<Diary> unDeletedDiaries = diariesMapByDate.get(date).stream()
              .filter(diary -> !diary.isDeleted())
              .toList();

      List<Diary> deletedDiaries = diariesMapByDate.get(date).stream()
              .filter(Diary::isDeleted)
              .toList();
      Reply reply = repliesMapByDate.get(date);


      if (reply != null && !reply.getReplyInfo().isDeleted() && reply.getIs_read()) {
        // 답장이 있고 읽음
        replyStatus = UserReplyReadStatus.READY_READ;
        diaryDayInfos.add(
                DiaryDayInfo.of(unDeletedDiaries.size(), replyStatus, date, getDiaryContentList(unDeletedDiaries),
                        false));
        continue;
      }

      if (reply != null && !reply.getReplyInfo().isDeleted() && !reply.getIs_read()) {
        // 답장이 있고 안읽음
        replyStatus = UserReplyReadStatus.READY_NOT_READ;
        diaryDayInfos.add(
                DiaryDayInfo.of(unDeletedDiaries.size(), replyStatus, date, getDiaryContentList(unDeletedDiaries),
                        false));
        continue;
      }

      if (reply == null && deletedDiaries.isEmpty() && !unDeletedDiaries.isEmpty()) {
        // 삭제한 적이 없고 일기를 쓴 채로 답장을 기다리는중
        replyStatus = UserReplyReadStatus.UNREADY;
        diaryDayInfos.add(
                DiaryDayInfo.of(unDeletedDiaries.size(), replyStatus, date, getDiaryContentList(unDeletedDiaries),
                        false));
        continue;
      }

      if (reply != null && reply.getReplyInfo().isDeleted() && !deletedDiaries.isEmpty() && !unDeletedDiaries.isEmpty()) {
        // 삭제한 적이 있고 일기를 다시 씀
        replyStatus = UserReplyReadStatus.UNREADY;
        diaryDayInfos.add(
                DiaryDayInfo.of(unDeletedDiaries.size(), replyStatus, date, getDiaryContentList(unDeletedDiaries),
                        true));
      }

    }
    return DiaryListInfo.of(getCloverCount(year), diaryDayInfos);
  }

  public DiaryListInfo getDiaryCalendar(int year, int month) {
    LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
    LocalDateTime end = start.plusMonths(1);


    List<Diary> diaries = diaryRepository.findDiariesByUserIdAndCreatedAtBetween(JwtUtil.getLoginMemberId(), start, end);
    List<Reply> replies = replyRepository.findByUserIdAndDiaryCreatedDateBetween(JwtUtil.getLoginMemberId(), start.toLocalDate(), end.toLocalDate());


    List<DiaryDayInfo> diaryDayInfos = getDiaryDayInfoList(year, month);
    Map<LocalDate, List<Diary>> diariesMapByDate = diaries.stream().collect(Collectors.groupingBy(diary -> diary.getCreatedAt().toLocalDate()));
    Map<LocalDate, Reply> repliesMapByDate = replies.stream().collect(Collectors.toMap(Reply::getDiaryCreatedDate, reply -> reply));
    UserReplyReadStatus replyStatus = null;

    for (int i = 0; i < getDaysInMonth(year, month); i ++) {
      LocalDate today = LocalDate.of(year, month, i + 1);
      List<Diary> t = diariesMapByDate.get(today);
      if(diariesMapByDate.get(today) == null){
        continue;
      }
      List<Diary> unDeletedDiaries = diariesMapByDate.get(today).stream()
              .filter(diary -> !diary.isDeleted())
              .toList();

      List<Diary> deletedDiaries = diariesMapByDate.get(today).stream()
              .filter(Diary::isDeleted)
              .toList();
      Reply reply = repliesMapByDate.get(today);

      if (reply != null && !reply.getReplyInfo().isDeleted() && reply.getIs_read()) {
        // 답장이 있고 읽음
        replyStatus = UserReplyReadStatus.READY_READ;
        diaryDayInfos.set(i, DiaryDayInfo.of(unDeletedDiaries.size(), replyStatus, today, new ArrayList<>(),
                false));
        continue;
      }

      if (reply != null && !reply.getReplyInfo().isDeleted() && !reply.getIs_read()) {
        // 답장이 있고 안읽음
        replyStatus = UserReplyReadStatus.READY_NOT_READ;
        diaryDayInfos.set(i, DiaryDayInfo.of(unDeletedDiaries.size(), replyStatus, today, new ArrayList<>(),
                false));
        continue;
      }

      if (reply == null && deletedDiaries.isEmpty() && !unDeletedDiaries.isEmpty()) {
        // 삭제한 적이 없고 일기를 쓴 채로 답장을 기다리는중
        replyStatus = UserReplyReadStatus.UNREADY;
        diaryDayInfos.set(i, DiaryDayInfo.of(unDeletedDiaries.size(), replyStatus, today, new ArrayList<>(),
                false));
        continue;
      }

      if (reply != null && reply.getReplyInfo().isDeleted() && unDeletedDiaries.isEmpty()) {
        // 일기를 삭제하고 아무것도 안함.
        replyStatus = UserReplyReadStatus.UNREADY;
        diaryDayInfos.set(i, DiaryDayInfo.of(0, replyStatus, today, new ArrayList<>(),
                true));
      }


      if (reply != null && reply.getReplyInfo().isDeleted() && !deletedDiaries.isEmpty() && !unDeletedDiaries.isEmpty()) {
        // 삭제한 적이 있고 일기를 다시 씀
        replyStatus = UserReplyReadStatus.UNREADY;
        diaryDayInfos.set(i, DiaryDayInfo.of(unDeletedDiaries.size(), replyStatus, today, new ArrayList<>(),
                true));
      }


    }

    return DiaryListInfo.of(getCloverCount(year), diaryDayInfos);
  }

  private int getCloverCount(int year) {
    LocalDateTime start = LocalDateTime.of(year, 1, 1, 0, 0);
    LocalDateTime end = start.plusYears(1);
    return replyRepository.findByUserIdAndDiaryCreatedDateBetween(JwtUtil.getLoginMemberId(), start.toLocalDate(),
                    end.toLocalDate()).stream().filter(Reply::isRead)  // is_read가 true 인 것만 필터링
            .toList().size();
  }

  private List<DiaryContent> getDiaryContentList(List<Diary> foundDiaries) {
    return foundDiaries.stream()
            .map(diary -> new DiaryContent(diary.getContent()))
            .toList();
  }

  private List<DiaryDayInfo> getDiaryDayInfoList(int year, int month) {
    List<DiaryDayInfo> diaryDayInfos = new ArrayList<>();
    int daysInMonth = getDaysInMonth(year, month);
    for (int i = 0; i < daysInMonth; i++) {
      diaryDayInfos.add(DiaryDayInfo.of(0, UserReplyReadStatus.UNREADY, LocalDate.of(year, month, i+1), new ArrayList<>())); // 빈 요소 추가
    }
    return diaryDayInfos;
  }
  private int getDaysInMonth(int year, int month) {
    return LocalDate.of(year, month, 1).lengthOfMonth();
  }

}
