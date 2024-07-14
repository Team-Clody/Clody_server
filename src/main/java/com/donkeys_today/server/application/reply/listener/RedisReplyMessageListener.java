package com.donkeys_today.server.application.reply.listener;

import com.donkeys_today.server.application.reply.listener.dto.DiaryListenedMessage;
import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisReplyMessageListener implements MessageListener {

  private final ObjectMapper objectMapper;
  private final RedisTemplate<String, String> redisTemplate;

  private final String EXPIRED_MESSAGE_KEY_PREFIX = "diaryMessage_expiry:";
  private final String MESSAGE_KEY_PREFIX = "diaryMessage:";

  @Override
  public void onMessage(Message message, byte[] pattern) {
    String expiredKey = new String(message.getBody());
    log.info("########## listen succeed ##########");

    Long diaryId = Long.parseLong(expiredKey.split(":")[1]);

    String nonExpiredKey = MESSAGE_KEY_PREFIX + diaryId;
    String value = redisTemplate.opsForValue().get(nonExpiredKey);

    validateMessagePrefix(expiredKey);
    DiaryListenedMessage listenedMessage = parseStringToDiaryListenedMessage(value);
    log.info("listenedMessage : userId = {}, message = {}, date = {}", listenedMessage.userId(),
        listenedMessage.message(), listenedMessage.date().toString());
  }

  private void validateMessagePrefix(String expiredKey) {
    if (expiredKey.startsWith(EXPIRED_MESSAGE_KEY_PREFIX)) {
      return;
    }
    throw new NotFoundException(ErrorType.DIARY_MESSAGE_NOT_FOUND);
  }

  private DiaryListenedMessage parseStringToDiaryListenedMessage(String value) {
    try {
      return objectMapper.readValue(value, DiaryListenedMessage.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
