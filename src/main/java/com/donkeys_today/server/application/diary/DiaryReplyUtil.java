package com.donkeys_today.server.application.diary;

import com.donkeys_today.server.application.user.UserService;
import com.donkeys_today.server.domain.diary.Diary;
import com.donkeys_today.server.domain.reply.Reply;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.infrastructure.diary.DiaryRepository;
import com.donkeys_today.server.infrastructure.reply.ReplyRepository;
import com.donkeys_today.server.presentation.diary.dto.response.DiaryContent;
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
    private final UserService userService;

    public Map<LocalDate, Reply> getRepliesByMonth(Long userId, int year, int month) {
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime end = start.plusMonths(1);

        List<Reply> replies = replyRepository.findByUserIdAndDiaryCreatedDateBetween(userId, start.toLocalDate(),
                end.toLocalDate());

        Map<LocalDate, Reply> repliesByDate = replies.stream()
                .collect(Collectors.toMap(Reply::getDiaryCreatedDate, reply -> reply));

        return repliesByDate;
    }

    public Map<LocalDate, List<Diary>> getDiariesByMonth(Long userId, int year, int month) {
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime end = start.plusMonths(1);

        User user = userService.getUserById(userId);
        List<Diary> diaries = diaryRepository.findByUserAndIsDeletedFalseAndCreatedAtBetween(user, start, end);

        Map<LocalDate, List<Diary>> diariesByDate = diaries.stream()
                .collect(Collectors.groupingBy(diary -> diary.getCreatedAt().toLocalDate()));
        return diariesByDate;
    }

    public List<DiaryContent> getDiaryByDate(Long userId, int year, int month, int day) {
        LocalDateTime start = LocalDateTime.of(year, month, day, 0, 0);
        LocalDateTime end = start.plusDays(1);
        User user = userService.getUserById(userId);
        List<DiaryContent> diaries = diaryRepository.findByUserAndIsDeletedFalseAndCreatedAtBetween(user, start, end).stream()
                .map(diary -> new DiaryContent(diary.getContent()))
                .collect(Collectors.toList());
        return diaries;
    }
}
