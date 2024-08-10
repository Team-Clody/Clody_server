package com.clody.domain.reply.event;

import com.clody.domain.config.ReplyProperty;
import com.clody.domain.diary.event.DefaultDiaryCreatedEvent;
import com.clody.domain.diary.event.DiaryDeletionEvent;
import com.clody.domain.diary.event.FirstDiaryCreatedEvent;
import com.clody.domain.diary.event.ProfanityDetectedDiaryEvent;
import com.clody.domain.reply.Reply;
import com.clody.domain.reply.repository.ReplyRepository;
import com.clody.domain.reply.service.ReplyConverter;
import com.clody.domain.user.User;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyEventListener {

  private final ReplyProperty replyProperty;

  private final ReplyRepository replyRepository;
  private final ReplyPublisher replyPublisher;

  @EventListener
  public void handleProfanityDetectedDiary(ProfanityDetectedDiaryEvent event) {
    User user = event.diaryList().getFirst().getUser();
    LocalDate createdDate = event.diaryList().getFirst().getCreatedAt().toLocalDate();
    Reply staticReply = Reply.createStaticReply(user, replyProperty.getContent(), createdDate);
    Reply savedReply = replyRepository.save(staticReply);
    replyPublisher.publish(new ReplyCreatedEvent(savedReply));
  }

  @EventListener
  public void handleFirstDiary(FirstDiaryCreatedEvent event) {
    User user = event.diaryList().getFirst().getUser();
    LocalDate createdDate = event.diaryList().getFirst().getCreatedAt().toLocalDate();
    String convertedContent = ReplyConverter.convertDiaryContentToReplyRequestForm(
        event.diaryList());
    Reply firstReply = Reply.createFastDynamicReply(user, createdDate);
    Reply savedReply = replyRepository.save(firstReply);
    replyPublisher.publish(new ReplyCreatedEvent(savedReply));
  }

  @EventListener
  public void handleDefaultDiary(DefaultDiaryCreatedEvent event) {
    User user = event.diaryList().getFirst().getUser();
    LocalDate createdDate = event.diaryList().getFirst().getCreatedAt().toLocalDate();
    Reply defaultReply = Reply.createDynamicReply(user, createdDate);
    Reply savedReply = replyRepository.save(defaultReply);
    replyRepository.save(defaultReply);
    replyPublisher.publish(new ReplyCreatedEvent(savedReply));
  }

  @EventListener
  public void handleDeletionRequest(DiaryDeletionEvent event) {
    replyRepository.deleteByUserIdAndDiaryCreatedDate(event.userId(), event.diaryCreationDate());
  }
}
