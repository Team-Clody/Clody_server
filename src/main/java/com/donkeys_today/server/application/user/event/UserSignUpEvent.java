package com.donkeys_today.server.application.user.event;

import com.donkeys_today.server.domain.user.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserSignUpEvent extends ApplicationEvent {

  private final User user;

  public UserSignUpEvent(Object source, User user) {
    super(source);
    this.user = user;
  }
}
