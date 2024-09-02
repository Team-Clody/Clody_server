package com.clody.clodyapi.reply.usecase;

import com.clody.domain.reply.dto.ReplyResponse;

public interface ReplyRetrieveUsecase {

  ReplyResponse getReplyByDate(int year, int month, int date);

}
