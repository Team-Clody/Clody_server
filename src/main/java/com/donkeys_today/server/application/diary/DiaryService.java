package com.donkeys_today.server.application.diary;

import com.donkeys_today.server.domain.diary.Diary;
import com.donkeys_today.server.domain.diary.DiaryRepository;
import com.donkeys_today.server.domain.diary.ReplyStatus;
import com.donkeys_today.server.domain.reply.Reply;
import com.donkeys_today.server.domain.reply.ReplyRepository;
import com.donkeys_today.server.presentation.Diary.dto.DiaryCalenderResponse;
import com.donkeys_today.server.presentation.Diary.dto.DiaryContent;
import com.donkeys_today.server.presentation.Diary.dto.DiaryFullInfo;
import com.donkeys_today.server.presentation.Diary.dto.DiaryListResponse;
import com.donkeys_today.server.presentation.Diary.dto.DiarySimpleInfo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final ReplyRepository replyRepository;

    public DiaryListResponse getDiaryList(int year, int month) {
//        SecurityContext context = SecurityContextHolder.getContext();
//        Authentication authentication = context.getAuthentication();
//        Long userId = Long.valueOf(authentication.getName());

        Long userId = 3L;
        List<Diary> diaries = diaryRepository.findContentsByUserIdAndYearAndMonth(userId, year, month);
        List<Reply> replies = replyRepository.findRepliesByUserIdAndYearAndMonth(userId, year, month);

        Map<LocalDate, Reply> repliesByDate = replies.stream()
                .collect(Collectors.toMap(Reply::getCreatedDate, reply -> reply));
        Map<LocalDate, List<Diary>> diariesByDate = diaries.stream()
                .collect(Collectors.groupingBy(diary -> diary.getCreatedAt().toLocalDate()));

        AtomicInteger totalMonthlyCount = new AtomicInteger();

        List<DiaryFullInfo> diaryData = new ArrayList<>();

        diariesByDate.forEach((k, v) -> {
            ReplyStatus replyStatus;
            if (repliesByDate.containsKey(k)) {
                Reply reply = repliesByDate.get(k);
                if (reply.getIs_read()) {
                    totalMonthlyCount.addAndGet(1);
                    replyStatus = ReplyStatus.READY_READ;
                } else { // 준비됐으나 안읽음
                    replyStatus = ReplyStatus.READY_NOT_READ;
                }
            } else {
                replyStatus = ReplyStatus.UNREADY;
            }

            diaryData.add(
                    DiaryFullInfo.of(v.size(), replyStatus, k,
                            v.stream().map(diary -> new DiaryContent(diary.getContent()))
                                    .toList()));

        });
        diaryData.sort(Comparator.comparing(DiaryFullInfo::date));

        return DiaryListResponse.of(totalMonthlyCount.get(), diaryData);
    }

    public DiaryCalenderResponse getDiaryCalender(int year, int month) {
//        SecurityContext context = SecurityContextHolder.getContext();
//        Authentication authentication = context.getAuthentication();
//        Long userId = Long.valueOf(authentication.getName());
        Long userId = 3L;
        List<Diary> diaries = diaryRepository.findContentsByUserIdAndYearAndMonth(userId, year, month);
        List<Reply> replies = replyRepository.findRepliesByUserIdAndYearAndMonth(userId, year, month);

        Map<LocalDate, Reply> repliesByDate = replies.stream()
                .collect(Collectors.toMap(Reply::getCreatedDate, reply -> reply));
        Map<LocalDate, List<Diary>> diariesByDate = diaries.stream()
                .collect(Collectors.groupingBy(diary -> diary.getCreatedAt().toLocalDate()));

        int daysInMonth = LocalDate.of(year, month, 1).lengthOfMonth();
        AtomicInteger totalMonthlyCount = new AtomicInteger();
        List<DiarySimpleInfo> diaryData = new ArrayList<>();
        for (int i = 0; i < daysInMonth; i++) {
            diaryData.add(DiarySimpleInfo.of(0, ReplyStatus.UNREADY)); // 빈 요소 추가

        }
        diariesByDate.forEach((k, v) -> {
            ReplyStatus replyStatus;
            if (repliesByDate.containsKey(k)) {
                Reply reply = repliesByDate.get(k);
                if (reply.getIs_read()) {
                    totalMonthlyCount.addAndGet(1);
                    replyStatus = ReplyStatus.READY_READ;
                } else { // 준비됐으나 안읽음
                    replyStatus = ReplyStatus.READY_NOT_READ;
                }
            } else {
                replyStatus = ReplyStatus.UNREADY;
            }
            int dayOfMonth = k.getDayOfMonth();
            DiarySimpleInfo diarySimpleInfo = DiarySimpleInfo.of(v.size(), replyStatus);
            diaryData.set(dayOfMonth - 1, diarySimpleInfo);

        });

        return DiaryCalenderResponse.of(totalMonthlyCount.get(), diaryData);
    }
}
