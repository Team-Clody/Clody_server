package com.clody.clodyapi.reply.mapper;

import com.clody.domain.reply.Reply;
import com.clody.domain.reply.dto.Message;
import com.clody.domain.reply.dto.ReplyInsertionInfo;

public class ReplyMapper {

  public static ReplyInsertionInfo toReplyInsertionInfo(Message message) {
    return ReplyInsertionInfo.of(message.replyId(), message.content(), message.version());
  }

  public static Message parseToMessage(Reply reply) {
    return Message.of(reply.getId(), reply.getContent(), reply.getVersion(),reply.getReplyType());
  }

}
