package com.clody.clodyapi.reply.service;

import com.clody.clodyapi.reply.mapper.ReplyMapper;
import com.clody.domain.reply.dto.Message;
import com.clody.domain.reply.dto.ReplyInsertionInfo;
import com.clody.domain.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyInsertionProcessor {

  private final ReplyService replyService;
//  private final AlarmProcessor alarmProcessor

  @EventListener
  public void insertReply(Message message){
    ReplyInsertionInfo info = ReplyMapper.toReplyInsertionInfo(message);
    replyService.insertReply(info);

    log.info("Reply Inserted: {}", info.content());
    //알림이벤트 전송
  }

}
