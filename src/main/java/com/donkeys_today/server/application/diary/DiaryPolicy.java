package com.donkeys_today.server.application.diary;

import com.donkeys_today.server.application.auth.JwtUtil;
import com.donkeys_today.server.application.user.UserService;
import com.donkeys_today.server.domain.diary.Diary;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.infrastructure.diary.DiaryRepository;
import com.donkeys_today.server.presentation.diary.dto.request.DiaryRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiaryPolicy {

  private final ProfanityFilter profanityFilter;
  private final DiaryRepository diaryRepository;
  private final DiaryRetriever diaryRetriever;
  private final UserService userService;

  public boolean checkUserInitialDiary(User user) {
    return diaryRepository.existsByUser(user);
  }

  public boolean check_fist_diary_and_first_reply(User user) {
    //유저 일기 전부 조회
    List<Diary> diaries = diaryRepository.findAllByUser(user);

    // 삭제되지 않은 일기 조회
    List<Diary> filteredDiaries = diaries.stream()
            .filter(diary -> !diary.isDeleted())
            .toList();

    // 삭제한 적이 있다면? 최초 답장 이 아님
    if (diaries.size() != filteredDiaries.size()) {
      return false;
    }

    // key 값이 1이 아니라면 일기를 두번 이상 쓴것
    Map<LocalDate, List<Diary>> map = filteredDiaries.stream()
            .collect(Collectors.groupingBy(diary -> diary.getCreatedAt().toLocalDate()));
    if (map.keySet().size() == 1) {
      return true;
    }
    return false;
  }

  public boolean containsProfanity(List<String> content) {
    if (profanityFilter.containsProfanity(content)) {
      return true;
    } else {
      return false;
    }
  }

  public boolean hasDeletedDiary(User user, String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.parse(date, formatter);
    LocalDateTime localDateTime = localDate.atStartOfDay();
    List<Diary> foundDiary = diaryRetriever.getTodayDiariesByUser(user, localDateTime);
    return foundDiary.stream().anyMatch(Diary::isDeleted);
  }

  public void updateDeletedDiary(User user, DiaryRequest request) {

    List<Diary> newDiaries = request.content().stream()
        .map(content -> Diary.builder()
            .isDeleted(false)
            .content(content)
            .user(user)
            .updatedAt(LocalDateTime.now())
            .createdAt(LocalDateTime.now())
            .build())
        .collect(Collectors.toUnmodifiableList());
    diaryRepository.saveAll(newDiaries);
  }

  public boolean hasDiary() {
    return !diaryRetriever.getTodayDiariesByUser(userService.getUserById(JwtUtil.getLoginMemberId()),
            LocalDateTime.now()).isEmpty();
  }
}
