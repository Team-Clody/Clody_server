package com.clody.domain.alarm.event;

import com.clody.domain.user.event.UserCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AlarmEventListener {

  @EventListener
  public void handleUserCreatedEvent(UserCreatedEvent event){
    //빈 알람 객체 생성 및 저장필요

  }

}
