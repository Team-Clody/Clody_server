package com.clody.domain.reply.repository;

import com.clody.domain.reply.dto.DequeuedMessage;

public interface ReplyMessageReceiver {

  void processMessage(DequeuedMessage message);
}
