package com.donkeys_today.server.application.diary;

import com.donkeys_today.server.application.auth.JwtUtil;
import com.donkeys_today.server.application.diary.dto.DiaryMessage;
import com.donkeys_today.server.application.user.UserService;
import com.donkeys_today.server.domain.diary.Diary;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.infrastructure.diary.DiaryRepository;
import com.donkeys_today.server.presentation.diary.dto.request.DiaryRequest;
import com.donkeys_today.server.presentation.diary.dto.response.DiaryResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryService {

  private final UserService userService;
  private final DiaryRepository diaryRepository;
  private final DiaryPublisher diaryPublisher;

  public DiaryResponse createDiary(DiaryRequest request) {
    //TODO 첫 요청일 경우, 즉시 답변 생성 (DB 전체 조회)

    //TODO 당일 일기를 삭제한 유저일 경우(당일 생성한 일기가 존재하고, is_deleted == true)면, 답변 생성하지 않음, (기존 일기만 업데이트)

    User user = userService.findUserById(JwtUtil.getLoginMemberId());
    log.info("diary ; {}", request.content());
    List<Diary> diaryList = request.content()
        .stream()
        .map(content -> Diary.builder()
            .user(user)
            .content(content)
            .build()
        ).collect(Collectors.toUnmodifiableList());
    diaryRepository.saveAll(diaryList);
    DiaryMessage message = diaryPublisher.convertDiariesToMessage(diaryList);
    diaryPublisher.publishDiaryMessage(message);

    LocalDateTime InitialCreatedTime = diaryList.get(0).getCreatedAt();
    return DiaryResponse.createLocalDateTimeToString(diaryList.getFirst().getCreatedAt());
  }

}
