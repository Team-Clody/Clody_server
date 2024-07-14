package com.donkeys_today.server.application.reply;

import com.donkeys_today.server.application.auth.JwtUtil;
import com.donkeys_today.server.application.user.UserService;
import com.donkeys_today.server.domain.reply.Reply;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.presentation.reply.dto.response.ReplyContent;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyService {

    private final ReplyRetriever replyRetriever;
    private final UserService userService;

    public ReplyContent getReply(int year, int month, int day) {
        User user = userService.getUserById(JwtUtil.getLoginMemberId());
        Reply reply = replyRetriever.getReplyByYearAndMonthAndDay(user, LocalDate.of(year, month, day));
        if (reply == null) {
            return null;
        }
        return ReplyContent.of(reply.getContent());

    }
}
