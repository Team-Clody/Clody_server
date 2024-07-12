package com.donkeys_today.server.application.diary;

import com.donkeys_today.server.domain.diary.Diary;
import com.donkeys_today.server.domain.diary.DiaryRepository;
import com.donkeys_today.server.domain.reply.Reply;
import com.donkeys_today.server.domain.reply.ReplyRepository;
import com.donkeys_today.server.presentation.Diary.dto.response.DiaryContent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class DiaryReplyUtil {

    private final DiaryRepository diaryRepository;
    private final ReplyRepository replyRepository;

    public Map<LocalDate, Reply> getRepliesByMonth(Long userId, int year, int month) {
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime end = start.plusMonths(1);

        List<Reply> replies = replyRepository.findByUserIdAndCreatedDateBetween(userId, start.toLocalDate(),
                end.toLocalDate());

        Map<LocalDate, Reply> repliesByDate = replies.stream()
                .collect(Collectors.toMap(Reply::getCreatedDate, reply -> reply));

        return repliesByDate;
    }

    public Map<LocalDate, List<Diary>> getDiariesByMonth(Long userId, int year, int month) {
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime end = start.plusMonths(1);

        List<Diary> diaries = diaryRepository.findByUserIdAndCreatedAtBetween(userId, start, end);

        Map<LocalDate, List<Diary>> diariesByDate = diaries.stream()
                .collect(Collectors.groupingBy(diary -> diary.getCreatedAt().toLocalDate()));
        return diariesByDate;
    }


    public List<DiaryContent> getDiaryByDate(Long userId, int year, int month, int day) {
        LocalDateTime start = LocalDateTime.of(year, month, day, 0, 0);
        LocalDateTime end = start.plusDays(1);
        List<DiaryContent> diaries = diaryRepository.findByUserIdAndCreatedAtBetween(userId, start, end).stream()
                .map(diary -> new DiaryContent(diary.getContent()))
                .collect(Collectors.toList());
        return diaries;
    }
}
