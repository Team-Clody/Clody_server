package com.clody.domain.reply.event;

import com.clody.domain.reply.dto.DequeuedMessage;
import com.clody.domain.reply.dto.Message;

public interface ReplyMessagePublisher {

  void publishMessage(Message message);

  void publishDequeuedMessage(DequeuedMessage message);
}
