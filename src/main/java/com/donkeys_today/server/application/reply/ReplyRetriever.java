package com.donkeys_today.server.application.reply;

import com.donkeys_today.server.domain.reply.Reply;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.infrastructure.reply.ReplyRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyRetriever {

    private final ReplyRepository replyRepository;

    public Reply getReplyByYearAndMonthAndDay(User user, LocalDate diaryCreatedDate) {

        return replyRepository.findByUserAndAndDiaryCreatedDate(user, diaryCreatedDate);
    }


}
