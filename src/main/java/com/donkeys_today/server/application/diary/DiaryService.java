package com.donkeys_today.server.application.diary;

import com.donkeys_today.server.application.auth.JwtUtil;
import com.donkeys_today.server.application.diary.dto.DiaryMessage;
import com.donkeys_today.server.application.user.UserService;
import com.donkeys_today.server.domain.diary.Diary;
import com.donkeys_today.server.domain.diary.DiaryPublisher;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.infrastructure.diary.DiaryRepository;
import com.donkeys_today.server.presentation.diary.dto.request.DiaryRequest;
import com.donkeys_today.server.presentation.diary.dto.response.DiaryResponse;
import java.time.LocalDateTime;
import java.util.List;
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
  private final DiaryPolicy diaryPolicy;
  private final DiaryRetriever diaryRetriever;
  private final DiaryCreator diaryCreator;

  public DiaryResponse createDiary(DiaryRequest request) {
    User user = userService.findUserById(JwtUtil.getLoginMemberId());

    //당일 일기를 삭제한 유저일 경우(당일 생성한 일기가 존재하고, is_deleted == true)면, 답변 생성하지 않음, (기존 일기만 업데이트)
    if (diaryPolicy.hasDeletedDiary(user, request.date())) {
      diaryPolicy.updateDeletedDiary(user, request);
      return DiaryResponse.createLocalDateTimeToString(LocalDateTime.now());
    }

    // 욕설을 포함한 일기를 작성한 경우, 정적 답변을 생성함 (기존 일기만 업데이트)
    if (diaryPolicy.containsProfanity(request.content())) {
      diaryCreator.saveAllDiary(user, request.content());
      createStaticReply(user);
      return DiaryResponse.createLocalDateTimeToString(LocalDateTime.now());
    }

    log.info("diary ; {}", request.content());
    List<Diary> diaryList = diaryCreator.saveAllDiary(user, request.content());
    DiaryMessage message = diaryPublisher.convertDiariesToMessage(diaryList);

    // 첫 요청일 경우, 즉시 답변 생성 (DB 전체 조회)
    if (diaryPolicy.checkUserInitialDiary(user)) {
      diaryPublisher.publishInitialDiaryMessage(message);
      return DiaryResponse.createLocalDateTimeToString(LocalDateTime.now());
    }

    diaryPublisher.publishDiaryMessage(message);
    return DiaryResponse.createLocalDateTimeToString(diaryList.getFirst().getCreatedAt());
  }

  public void createStaticReply(User user) {
    List<String> contents = List.of("욕설 노노","욕설 노노","파이팅","행복하자","건강한 삶");
    diaryCreator.saveAllDiary(user, contents);
  }
}
