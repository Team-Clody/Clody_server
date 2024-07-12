package com.donkeys_today.server.application.diary;

import com.donkeys_today.server.domain.diary.Diary;
import com.donkeys_today.server.domain.diary.ReplyStatus;
import com.donkeys_today.server.domain.reply.Reply;
import com.donkeys_today.server.presentation.Diary.dto.response.DiaryCalenderResponse;
import com.donkeys_today.server.presentation.Diary.dto.response.DiaryContent;
import com.donkeys_today.server.presentation.Diary.dto.response.DiaryFullInfo;
import com.donkeys_today.server.presentation.Diary.dto.response.DiaryListResponse;
import com.donkeys_today.server.presentation.Diary.dto.response.DiaryResponse;
import com.donkeys_today.server.presentation.Diary.dto.response.DiarySimpleInfo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryReplyUtil diaryReplyUtil;

    public DiaryListResponse getDiaryList(int year, int month) {

        Map<LocalDate, List<Diary>> diariesByDate = diaryReplyUtil.getDiariesByMonth(userId, year, month);
        Map<LocalDate, Reply> repliesByDate = diaryReplyUtil.getRepliesByMonth(userId, year, month);

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

    public DiaryCalenderResponse getDiaryCalender(int year, int month) {

        Map<LocalDate, List<Diary>> diariesByDate = diaryReplyUtil.getDiariesByMonth(userId, year, month);
        Map<LocalDate, Reply> repliesByDate = diaryReplyUtil.getRepliesByMonth(userId, year, month);

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
        return DiaryCalenderResponse.of(totalMonthlyCount.get(), diaryData);
    }

    private static DiaryFullInfo getDiaryFullInfo(LocalDate date, List<Diary> dairies, ReplyStatus replyStatus) {
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

    public DiaryResponse getDiary(int year, int month, int day) {

        List<DiaryContent> diaries = diaryReplyUtil.getDiaryByDate(userId, year, month, day);
        return DiaryResponse.of(diaries);
    }
}
