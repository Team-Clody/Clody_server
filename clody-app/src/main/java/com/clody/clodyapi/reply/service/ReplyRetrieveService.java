package com.clody.clodyapi.reply.service;

import com.clody.clodyapi.reply.mapper.ReplyMapper;
import com.clody.clodyapi.reply.usecase.ReplyRetrieveUsecase;
import com.clody.domain.reply.dto.ReplyResponse;
import com.clody.domain.reply.service.ReplyQueryService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyRetrieveService implements ReplyRetrieveUsecase {

  private final ReplyQueryService replyQueryService;

  @Override
  public ReplyResponse getReplyByDate(int year, int month, int date) {

    LocalDate requestDate = ReplyMapper.parseToLocalDate(year, month, date);
    return replyQueryService.retrieveReplyByDate(requestDate);
  }
}
