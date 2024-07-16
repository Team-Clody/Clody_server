package com.donkeys_today.server.application.diary;

import com.donkeys_today.server.application.auth.JwtUtil;
import com.donkeys_today.server.application.diary.dto.DiaryMessage;
import com.donkeys_today.server.application.reply.ReplyService;
import com.donkeys_today.server.application.user.UserService;
import com.donkeys_today.server.domain.diary.Diary;
import com.donkeys_today.server.domain.diary.DiaryPublisher;
import com.donkeys_today.server.domain.diary.ReplyStatus;
import com.donkeys_today.server.domain.reply.Reply;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.presentation.diary.dto.request.DiaryRequest;
import com.donkeys_today.server.presentation.diary.dto.response.DiaryCalendarResponse;
import com.donkeys_today.server.presentation.diary.dto.response.DiaryContent;
import com.donkeys_today.server.presentation.diary.dto.response.DiaryCreatedResponse;
import com.donkeys_today.server.presentation.diary.dto.response.DiaryFullInfo;
import com.donkeys_today.server.presentation.diary.dto.response.DiaryListResponse;
import com.donkeys_today.server.presentation.diary.dto.response.DiaryResponse;
import com.donkeys_today.server.presentation.diary.dto.response.DiarySimpleInfo;
import com.donkeys_today.server.presentation.user.dto.response.DiaryCreatedTimeGetResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryService {


  private final DiaryCreator diaryCreator;
  private final DiaryRetriever diaryRetriever;
  private final DiaryReplyUtil diaryReplyUtil;
  private final DiaryRemover diaryRemover;
  private final DiaryPolicy diaryPolicy;
  private final DiaryPublisher diaryPublisher;

  private final UserService userService;
  private final ReplyService replyService;

  private static DiaryFullInfo getDiaryFullInfo(LocalDate date, List<Diary> dairies,
      ReplyStatus replyStatus) {
    return DiaryFullInfo.of(dairies.size(), replyStatus, date,
        dairies.stream().map(diary -> new DiaryContent(diary.getContent()))
            .toList());
  }

  private static void plusCount(AtomicInteger totalMonthlyCount) {
    totalMonthlyCount.addAndGet(1);
  }

  private static Boolean isRead(Reply reply) {
    return reply.getIs_read();
  }

  private static Reply getReply(LocalDate date, Map<LocalDate, Reply> repliesByDate) {
    return repliesByDate.get(date);
  }

  private static boolean isReplyReady(LocalDate date, Map<LocalDate, Reply> repliesByDate) {
    return repliesByDate.containsKey(date);
  }

  private static void setDiarySimpleInfo(int day, List<Diary> diaries, ReplyStatus replyStatus,
      List<DiarySimpleInfo> diaryData) {

    DiarySimpleInfo diarySimpleInfo = DiarySimpleInfo.of(diaries.size(), replyStatus);
    diaryData.set(day - 1, diarySimpleInfo);
  }

  public DiaryListResponse getDiaryList(int year, int month) {

    Map<LocalDate, List<Diary>> diariesByDate = diaryReplyUtil.getDiariesByMonth(getUserId(), year,
        month);
    Map<LocalDate, Reply> repliesByDate = diaryReplyUtil.getRepliesByMonth(getUserId(), year,
        month);

    AtomicInteger totalMonthlyCount = new AtomicInteger();
    List<DiaryFullInfo> diaryData = new ArrayList<>();
    diariesByDate.forEach((date, dairies) -> {
      ReplyStatus replyStatus;
      if (isReplyReady(date, repliesByDate)) {
        Reply reply = getReply(date, repliesByDate);
        if (isRead(reply)) {
          plusCount(totalMonthlyCount);
          replyStatus = ReplyStatus.READY_READ;
        } else { // 준비됐으나 안읽음
          replyStatus = ReplyStatus.READY_NOT_READ;
        }
      } else {
        replyStatus = ReplyStatus.UNREADY;
      }
      diaryData.add(getDiaryFullInfo(date, dairies, replyStatus));

    });
    diaryData.sort(Comparator.comparing(DiaryFullInfo::date));
    return DiaryListResponse.of(totalMonthlyCount.get(), diaryData);
  }

  public DiaryCalendarResponse getDiaryCalendar(int year, int month) {

    Map<LocalDate, List<Diary>> diariesByDate = diaryReplyUtil.getDiariesByMonth(getUserId(), year,
        month);
    Map<LocalDate, Reply> repliesByDate = diaryReplyUtil.getRepliesByMonth(getUserId(), year,
        month);

    int daysInMonth = LocalDate.of(year, month, 1).lengthOfMonth();
    AtomicInteger totalMonthlyCount = new AtomicInteger();
    List<DiarySimpleInfo> diaryData = new ArrayList<>();
    for (int i = 0; i < daysInMonth; i++) {
      diaryData.add(DiarySimpleInfo.of(0, ReplyStatus.UNREADY)); // 빈 요소 추가
    }

    diariesByDate.forEach((date, diaries) -> {
      ReplyStatus replyStatus;
      if (isReplyReady(date, repliesByDate)) {
        Reply reply = getReply(date, repliesByDate);
        if (isRead(reply)) {
          plusCount(totalMonthlyCount);
          replyStatus = ReplyStatus.READY_READ;
        } else { // 준비됐으나 안읽음
          replyStatus = ReplyStatus.READY_NOT_READ;
        }
      } else {
        replyStatus = ReplyStatus.UNREADY;
      }

      int day = date.getDayOfMonth();
      setDiarySimpleInfo(day, diaries, replyStatus, diaryData);
    });
    return DiaryCalendarResponse.of(totalMonthlyCount.get(), diaryData);
  }

  public DiaryResponse getDiary(int year, int month, int day) {

    List<DiaryContent> diaries = diaryReplyUtil.getDiaryByDate(getUserId(), year, month, day);
    return DiaryResponse.of(diaries);
  }

  public Long getUserId() {
    return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
  }

  public DiaryCreatedResponse createDiary(DiaryRequest request) {
    User user = userService.getUserById(JwtUtil.getLoginMemberId());

    //당일 일기를 삭제한 유저일 경우(당일 생성한 일기가 존재하고, is_deleted == true)면, 답변 생성하지 않음, (기존 일기만 업데이트)
    if (diaryPolicy.hasDeletedDiary(user, request.date())) {
      diaryPolicy.updateDeletedDiary(user, request);
      return DiaryCreatedResponse.createLocalDateTimeToString(LocalDateTime.now());
    }

    // 욕설을 포함한 일기를 작성한 경우, 정적 답변을 생성함 (기존 일기만 업데이트)
    if (diaryPolicy.containsProfanity(request.content())) {
      diaryCreator.saveAllDiary(user, request.content());
      createStaticReply(user);
      return DiaryCreatedResponse.createLocalDateTimeToString(LocalDateTime.now());
    }

    log.info("diary ; {}", request.content());
    List<Diary> diaryList = diaryCreator.saveAllDiary(user, request.content());
    DiaryMessage message = diaryPublisher.convertDiariesToMessage(diaryList);

    // 첫 요청일 경우, 즉시 답변 생성 (DB 전체 조회)
    if (diaryPolicy.checkUserInitialDiary(user)) {
      diaryPublisher.publishInitialDiaryMessage(message);
      return DiaryCreatedResponse.createLocalDateTimeToString(LocalDateTime.now());
    }

    diaryPublisher.publishDiaryMessage(message);
    return DiaryCreatedResponse.createLocalDateTimeToString(diaryList.getFirst().getCreatedAt());
  }

  public void createStaticReply(User user) {
    List<String> contents = List.of("욕설 노노", "욕설 노노", "파이팅", "행복하자", "건강한 삶");
    diaryCreator.saveAllDiary(user, contents);
  }

  @Transactional
  public void deleteDiary(int year, int month, int date) {
    User user = userService.getUserById(JwtUtil.getLoginMemberId());
    LocalDateTime currentTime = LocalDateTime.of(LocalDate.of(year, month, date),
        LocalDateTime.now().toLocalTime());
    log.info("currentTime : {}", currentTime);
    List<Diary> diaryList = diaryRetriever.getTodayDiariesByUser(user,
        currentTime);
    diaryRemover.removeDiarySoft(diaryList);

    if (diaryPublisher.containsKey(user.getId())) {
      diaryPublisher.removeDiary(user.getId());
    }

    if (replyService.isReplyExist(user.getId(), year, month, date)) {
      replyService.removeReply(user.getId(), year, month, date);
    }
  }

  public DiaryCreatedTimeGetResponse getDiaryCreatedTime(int year, int month, int date) {

    User user = userService.getUserById(JwtUtil.getLoginMemberId());
    LocalDateTime start = LocalDateTime.of(year, month, date, 0, 0);
    LocalDateTime end = start.plusDays(1);
    List<Diary> findDiaries = diaryRetriever.getDiariesByUserAndDateBetween(user, start, end);
    Diary diary = findDiaries.getFirst();
    LocalDateTime createdTime = diary.getCreatedAt();
    int HH = createdTime.getHour();
    int MM = createdTime.getMinute();
    int SS = createdTime.getSecond();
    return DiaryCreatedTimeGetResponse.of(HH, MM, SS);
  }
}
