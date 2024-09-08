package com.clody.domain.diary.service;

import com.clody.domain.diary.Diary;
import com.clody.domain.diary.dto.DiaryContent;
import com.clody.domain.diary.dto.DiaryDateInfo;
import com.clody.domain.diary.dto.response.DiaryDayInfo;
import com.clody.domain.diary.dto.response.DiaryListInfo;
import com.clody.domain.diary.dto.response.DiaryCreatedInfo;
import com.clody.domain.diary.repository.DiaryRepository;
import com.clody.domain.reply.Reply;
import com.clody.domain.reply.ReplyProcessStatus;
import com.clody.domain.reply.ReplyType;
import com.clody.domain.reply.UserReplyReadStatus;
import com.clody.domain.reply.repository.ReplyRepository;
import com.clody.support.security.util.JwtUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryQueryService {

  private final DiaryRepository diaryRepository;
  private final ReplyRepository replyRepository;

  public DiaryCreatedInfo getCreatedTime(DiaryDateInfo info) {

    Long userId = JwtUtil.getLoginMemberId();
    LocalDateTime localDateTime = info.parseToLocalDateTime();
    List<Diary> diaryList = diaryRepository.findTodayDiary(localDateTime);
    Diary latestDiary = Diary.getLatestDiary(diaryList);
    Reply reply = replyRepository.findByUserIdAndDiaryCreatedDate(userId, localDateTime.toLocalDate());
    return DiaryCreatedInfo.from(latestDiary.getCreatedAt(),reply.checkIfFirstReply());
  }

  public List<DiaryContent> getDiary(DiaryDateInfo info) {
    LocalDateTime localDateTime = info.parseToLocalDateTime();
    List<Diary> diaryList = diaryRepository.findTodayDiary(localDateTime);
    return diaryList.stream()
        .map(diary -> DiaryContent.of(diary.getContent()))
        .collect(Collectors.toUnmodifiableList());
  }

  public boolean isUserHasDeletedDiary(DiaryDateInfo info){
    LocalDateTime localDateTime = info.parseToLocalDateTime();
    return diaryRepository.findIfUserHasDeletedDiary(localDateTime);
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

      // date의 모든 일기중 삭제 되지 않은 일기
      List<Diary> unDeletedDiaries = diariesMapByDate.get(date).stream()
              .filter(diary -> !diary.isDeleted())
              .toList();

      // date 모든 일기중 삭제된 일기
      List<Diary> deletedDiaries = diariesMapByDate.get(date).stream()
              .filter(Diary::isDeleted)
              .toList();

      // date의 답변
      Reply reply = repliesMapByDate.get(date);


      if (deletedDiaries.isEmpty() && reply != null && !reply.getReplyInfo().isDeleted() && reply.getIs_read() && reply.getReplyInfo().getReplyProcessStatus().equals(ReplyProcessStatus.SUCCEED)) {
        // 일기 삭제한 적 없음 + 답장 있음 + 답장 읽음 + 답장 상태 SUCCESS임
        replyStatus = UserReplyReadStatus.READY_READ;
        diaryDayInfos.add(
                DiaryDayInfo.of(unDeletedDiaries.size(), replyStatus, date, getDiaryContentList(unDeletedDiaries),
                        false));
        continue;
      }

      // 1분
      // 12시간
      if (deletedDiaries.isEmpty() && reply != null && !reply.getReplyInfo().isDeleted() && !reply.getIs_read() && reply.getReplyInfo().getReplyProcessStatus().equals(ReplyProcessStatus.SUCCEED)) {

        if(reply.getReplyType().equals(ReplyType.FIRST) && (LocalDateTime.now().isBefore(unDeletedDiaries.get(0).getCreatedAt().plusMinutes(1)))){
          replyStatus = UserReplyReadStatus.UNREADY;
        } else if (reply.getReplyType().equals(ReplyType.DYNAMIC) && (LocalDateTime.now().isBefore(unDeletedDiaries.get(0).getCreatedAt().plusHours(12)))) {
          replyStatus = UserReplyReadStatus.UNREADY;
        } else{
          // 일기 삭제한 적 없음 + 답장 있음 + 답장 안읽음 + 답장 상태 SUCCESS 임
          replyStatus = UserReplyReadStatus.READY_NOT_READ;
        }
        diaryDayInfos.add(
                DiaryDayInfo.of(unDeletedDiaries.size(), replyStatus, date, getDiaryContentList(unDeletedDiaries),
                        false));
        continue;
      }

      if (deletedDiaries.isEmpty() && reply != null && !reply.getReplyInfo().isDeleted() && reply.getReplyInfo().getReplyProcessStatus().equals(ReplyProcessStatus.PENDING)) {
        // 일기 삭제한 적이 없음 + 답장 안옴
        replyStatus = UserReplyReadStatus.UNREADY;
        diaryDayInfos.add(
                DiaryDayInfo.of(unDeletedDiaries.size(), replyStatus, date, getDiaryContentList(unDeletedDiaries),
                        false));
        continue;
      }


      if (!deletedDiaries.isEmpty() && reply != null && reply.getReplyInfo().isDeleted() && !unDeletedDiaries.isEmpty()) {
        // 삭제한 적이 있고 일기를 다시 씀
        replyStatus = UserReplyReadStatus.UNREADY;
        diaryDayInfos.add(
                DiaryDayInfo.of(unDeletedDiaries.size(), replyStatus, date, getDiaryContentList(unDeletedDiaries),
                        true));
        continue;
      }

      if (!deletedDiaries.isEmpty() && reply != null && reply.getReplyInfo().isDeleted() && unDeletedDiaries.isEmpty()) {
        // 일기를 삭제하고 아무것도 안함
//        replyStatus = UserReplyReadStatus.UNREADY;
//        diaryDayInfos.add(
//                DiaryDayInfo.of(0, replyStatus, date, getDiaryContentList(unDeletedDiaries),
//                        true));
        continue;
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


      if (deletedDiaries.isEmpty() && reply != null && !reply.getReplyInfo().isDeleted() && reply.getIs_read() && reply.getReplyInfo().getReplyProcessStatus().equals(ReplyProcessStatus.SUCCEED)) {
        // 일기 삭제한 적 없음 + 답장 있음 + 답장 읽음 + 답장 상태 SUCCESS임
        replyStatus = UserReplyReadStatus.READY_READ;
        diaryDayInfos.set(i, DiaryDayInfo.of(unDeletedDiaries.size(), replyStatus, today, new ArrayList<>(),
                false));
        continue;
      }

      if (deletedDiaries.isEmpty() && reply != null && !reply.getReplyInfo().isDeleted() && !reply.getIs_read() && reply.getReplyInfo().getReplyProcessStatus().equals(ReplyProcessStatus.SUCCEED)) {
        // 일기 삭제한 적 없음 + 답장 있음 + 답장 안읽음 + 답장 상태 SUCCESS 임
        replyStatus = UserReplyReadStatus.READY_NOT_READ;
        diaryDayInfos.set(i, DiaryDayInfo.of(unDeletedDiaries.size(), replyStatus, today, new ArrayList<>(),
                false));
        continue;
      }

      if (deletedDiaries.isEmpty() && reply != null && !reply.getReplyInfo().isDeleted() && reply.getReplyInfo().getReplyProcessStatus().equals(ReplyProcessStatus.PENDING)) {
        // 일기 삭제한 적이 없음 + 답장 안옴
        replyStatus = UserReplyReadStatus.UNREADY;
        diaryDayInfos.set(i, DiaryDayInfo.of(unDeletedDiaries.size(), replyStatus, today, new ArrayList<>(),
                false));
        continue;
      }

      if (!deletedDiaries.isEmpty() && reply != null && reply.getReplyInfo().isDeleted() && !unDeletedDiaries.isEmpty()) {
        // 삭제한 적이 있고 일기를 다시 씀
        replyStatus = UserReplyReadStatus.UNREADY;
        diaryDayInfos.set(i, DiaryDayInfo.of(unDeletedDiaries.size(), replyStatus, today, new ArrayList<>(),
                true));
        continue;
      }

      if (!deletedDiaries.isEmpty() && reply != null && reply.getReplyInfo().isDeleted() && unDeletedDiaries.isEmpty()) {
        // 일기를 삭제하고 아무것도 안함
        replyStatus = UserReplyReadStatus.UNREADY;
        diaryDayInfos.set(i, DiaryDayInfo.of(0, replyStatus, today, new ArrayList<>(),
                true));
        continue;
      }

    }

    return DiaryListInfo.of(getCloverCount(year), diaryDayInfos);
  }

  private int getCloverCount(int year) {
    LocalDateTime start = LocalDateTime.of(year, 1, 1, 0, 0);
    LocalDateTime end = start.plusYears(1);
    return replyRepository.findByUserIdAndDiaryCreatedDateBetween(JwtUtil.getLoginMemberId(), start.toLocalDate(),
                    end.toLocalDate()).stream().filter(Reply::getIs_read).filter(reply -> !reply.getReplyInfo().isDeleted())  // is_read가 true 인 것만 필터링
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
