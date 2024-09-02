package com.clody.clodyapi.reply.mapper;

import com.clody.domain.reply.Reply;
import com.clody.domain.reply.dto.Message;
import com.clody.domain.reply.dto.ReplyInsertionInfo;
import java.time.LocalDate;

public class ReplyMapper {

  public static ReplyInsertionInfo toReplyInsertionInfo(Message message) {
    return ReplyInsertionInfo.of(message.replyId(), message.content(), message.version());
  }

  public static Message parseToMessage(Reply reply) {
    return Message.of(reply.getId(),reply.getUser().getId(), reply.getContent(), reply.getVersion(),reply.getReplyType());
  }

  public static LocalDate parseToLocalDate(int year, int month, int date){
    return LocalDate.of(year, month, date);
  }

}
