package com.clody.domain.user.event;

import com.clody.domain.user.User;
import lombok.Getter;

@Getter
public class UserCreatedEvent {

  private final User user;

  public UserCreatedEvent(User user) {
    this.user = user;
  }
}
