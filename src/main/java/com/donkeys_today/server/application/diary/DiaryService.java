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
import com.donkeys_today.server.presentation.diary.dto.response.DiaryCalenderGetResponse;
import com.donkeys_today.server.presentation.diary.dto.response.DiaryContent;
import com.donkeys_today.server.presentation.diary.dto.response.DiaryCreatedResponse;
import com.donkeys_today.server.presentation.diary.dto.response.DiaryFullInfo;
import com.donkeys_today.server.presentation.diary.dto.response.DiaryListGetResponse;
import com.donkeys_today.server.presentation.diary.dto.response.DiaryResponse;
import com.donkeys_today.server.presentation.diary.dto.response.DiarySimpleInfo;
import com.donkeys_today.server.presentation.user.dto.response.DiaryCreatedTimeGetResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryService {

  private final UserService userService;
  private final DiaryPublisher diaryPublisher;

  private final ReplyService replyService;
  private final DiaryRetriever diaryRetriever;
  private final DiaryPolicy diaryPolicy;
  private final DiaryCreator diaryCreator;
  private final DiaryRemover diaryRemover;

  public DiaryListGetResponse getDiaryList(int year, int month) {
    Map<LocalDate, List<Diary>> diariesByDate = getDiariesMap(year, month);
    Map<LocalDate, Reply> repliesByDate = getRepliesMap(year, month);

    List<DiaryFullInfo> diaryFullInfos = new ArrayList<>();
    int totalMonthlyCount = 0;

    for (LocalDate date : diariesByDate.keySet()) {
      List<Diary> foundDiaries = diariesByDate.get(date);
      int diaryCount = foundDiaries.size();
      ReplyStatus replyStatus = null;
      if (isReplyExist(date, repliesByDate) && isReplyRead(date, repliesByDate)) {
        totalMonthlyCount += 1;
        replyStatus = ReplyStatus.READY_READ;
      } else if (isReplyExist(date, repliesByDate)) {
        replyStatus = ReplyStatus.READY_NOT_READ;
      } else {
        replyStatus = ReplyStatus.UNREADY;
      }
      List<DiaryContent> diaryContents = getDiaryContentList(foundDiaries);
      DiaryFullInfo diaryFullInfo = DiaryFullInfo.of(diaryCount, replyStatus, date, diaryContents);
      diaryFullInfos.add(diaryFullInfo);
    }

    diaryFullInfos.sort(Comparator.comparing(DiaryFullInfo::date));
    return DiaryListGetResponse.of(totalMonthlyCount, diaryFullInfos);
  }

  public DiaryCalenderGetResponse getDiaryCalender(int year, int month) {

    Map<LocalDate, List<Diary>> diariesByDate = getDiariesMap(year, month);
    Map<LocalDate, Reply> repliesByDate = getRepliesMap(year, month);

    List<DiarySimpleInfo> diarySimpleInfos = getDiarySimpleInfoList(year, month);
    int totalMonthlyCount = 0;

    for (LocalDate date : diariesByDate.keySet()) {
      List<Diary> foundDiaries = diariesByDate.get(date);
      int diaryCount = foundDiaries.size();
      ReplyStatus replyStatus = null;
      if (isReplyExist(date, repliesByDate) && isReplyRead(date, repliesByDate)) {
        totalMonthlyCount += 1;
        replyStatus = ReplyStatus.READY_READ;
      } else if (isReplyExist(date, repliesByDate)) {
        replyStatus = ReplyStatus.READY_NOT_READ;
      } else {
        replyStatus = ReplyStatus.UNREADY;
      }

      DiarySimpleInfo diarySimpleInfo = DiarySimpleInfo.of(diaryCount, replyStatus);
      diarySimpleInfos.set(date.getDayOfMonth() - 1, diarySimpleInfo);
    }
    return DiaryCalenderGetResponse.of(totalMonthlyCount, diarySimpleInfos);
  }

  public DiaryResponse getDiary(int year, int month, int day) {
    LocalDateTime start = LocalDateTime.of(year, month, day, 0, 0);
    LocalDateTime end = start.plusDays(1);
    User user = userService.getUserById(JwtUtil.getLoginMemberId());
    List<Diary> diaries = diaryRetriever.getDiariesByUserAndDateBetween(user, start, end);
    List<Diary> notDeletedDiaries = diaryRetriever.findDiariesNotDeleted(diaries);
    return DiaryResponse.of(getDiaryContentList(notDeletedDiaries));
  }

  public DiaryCreatedResponse createDiary(DiaryRequest request) {
    User user = userService.getUserById(JwtUtil.getLoginMemberId());
    LocalDateTime createdAt = combineTime(request.date());

    //당일 일기를 삭제한 유저일 경우(당일 생성한 일기가 존재하고, is_deleted == true)면, 답변 생성하지 않음, (기존 일기만 업데이트)
    if (diaryPolicy.hasDeletedDiary(user, request.date())) {
      diaryPolicy.updateDeletedDiary(user, request);
      return DiaryCreatedResponse.createDiaryWithoutReply(LocalDateTime.now());
    }

    // 욕설을 포함한 일기를 작성한 경우, 정적 답변을 생성함 (기존 일기만 업데이트)
    if (diaryPolicy.containsProfanity(request.content())) {
      diaryCreator.saveAllDiary(user, request.content(), createdAt);
      createStaticReply(user, request.date());
      return DiaryCreatedResponse.createDiaryWithStaticReply(LocalDateTime.now());
    }

    log.info("diary ; {}", request.content());
    List<Diary> diaryList = diaryCreator.saveAllDiary(user, request.content(), createdAt);
    DiaryMessage message = diaryPublisher.convertDiariesToMessage(diaryList);

    // 첫 요청일 경우, 즉시 답변 생성 (DB 전체 조회)
    if (diaryPolicy.checkUserInitialDiary(user)) {
      diaryPublisher.publishInitialDiaryMessage(message);
      return DiaryCreatedResponse.createDiaryWithDynamicReply(LocalDateTime.now());
    }

    diaryPublisher.publishDiaryMessage(message);
    return DiaryCreatedResponse.createDiaryWithDynamicReply(LocalDateTime.now());
  }

  public void createStaticReply(User user, String date) {
    replyService.createStaticReply(user, date);

    // 정적 답변 생성시 어떻게 되는지 ?
  }

  @Transactional
  public DiaryResponse deleteDiary(int year, int month, int date) {
    User user = userService.getUserById(JwtUtil.getLoginMemberId());
    LocalDate parsedDate = LocalDate.of(year, month, date);
    LocalDateTime startOfDay = parsedDate.atStartOfDay();
    LocalDateTime endOfDay = parsedDate.atTime(LocalTime.MAX);

    log.info("currentTime : {}, startOfDay : {}, endOfDat : {}", parsedDate, startOfDay,endOfDay);
    List<Diary> diaryList = diaryRetriever.getDiariesByUserAndDateBetween(user,
        startOfDay,endOfDay);
    diaryRemover.removeDiarySoft(diaryList);

    if (diaryPublisher.containsKey(user.getId())) {
      diaryPublisher.removeDiary(user.getId());
    }

    if (replyService.isReplyExist(user.getId(), year, month, date)) {
      replyService.removeReply(user.getId(), year, month, date);
    }

    return DiaryResponse.of(List.of());
  }

  public DiaryCreatedTimeGetResponse getDiaryCreatedTime(int year, int month, int date) {

    User user = userService.getUserById(JwtUtil.getLoginMemberId());
    LocalDateTime start = LocalDateTime.of(year, month, date, 0, 0);
    LocalDateTime end = start.plusDays(1);
    List<Diary> diaries = diaryRetriever.getDiariesByUserAndDateBetween(user, start, end);
    List<Diary> filteredDiaries = diaries.stream()
            .filter(diary -> !diary.isDeleted())
            .toList();

    LocalDateTime createdTime = filteredDiaries.getFirst().getCreatedAt();
    return DiaryCreatedTimeGetResponse.of(createdTime.getHour(), createdTime.getMinute(),
        createdTime.getSecond());
  }

  private Map<LocalDate, List<Diary>> getDiariesMap(int year, int month) {
    LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
    LocalDateTime end = start.plusMonths(1);
    User user = userService.getUserById(JwtUtil.getLoginMemberId());
    List<Diary> diaries = diaryRetriever.getDiariesByUserAndDateBetween(user, start, end);
    List<Diary> filteredDiaries = diaries.stream()
            .filter(diary -> !diary.isDeleted())
            .toList();

    return filteredDiaries.stream()
        .collect(Collectors.groupingBy(diary -> diary.getCreatedAt().toLocalDate()));
  }

  private Map<LocalDate, Reply> getRepliesMap(int year, int month) {
    LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
    LocalDateTime end = start.plusMonths(1);
    User user = userService.getUserById(JwtUtil.getLoginMemberId());
    List<Reply> replies = replyService.getRepliesByUserAndDateBetween(user, start.toLocalDate(),
        end.toLocalDate());
    return replies.stream()
        .collect(Collectors.toMap(Reply::getDiaryCreatedDate, reply -> reply));
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

  private List<DiarySimpleInfo> getDiarySimpleInfoList(int year, int month) {
    List<DiarySimpleInfo> diarySimpleInfos = new ArrayList<>();
    int daysInMonth = getDaysInMonth(year, month);
    for (int i = 0; i < daysInMonth; i++) {
      diarySimpleInfos.add(DiarySimpleInfo.of(0, ReplyStatus.UNREADY)); // 빈 요소 추가
    }
    return diarySimpleInfos;
  }

  private int getDaysInMonth(int year, int month) {
    return LocalDate.of(year, month, 1).lengthOfMonth();
  }

  private LocalDateTime combineTime(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate requestedDate = LocalDate.parse(date, formatter);
    LocalTime currentTime = LocalTime.now();
    return LocalDateTime.of(requestedDate, currentTime);
  }

}
