package com.clody.domain.reply.service;

import com.clody.domain.reply.dto.DequeuedMessage;

public interface RodyProcessor {

  void createReply(DequeuedMessage content);

}
