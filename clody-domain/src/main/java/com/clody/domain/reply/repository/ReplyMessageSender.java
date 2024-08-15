package com.clody.domain.reply.repository;

public interface ReplyMessageSender<T> {

  void sendMessage(T message)  ;

}
