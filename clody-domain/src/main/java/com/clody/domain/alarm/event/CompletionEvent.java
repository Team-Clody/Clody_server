package com.clody.domain.alarm.event;

import com.clody.domain.user.User;

public record CompletionEvent(
    User user,
    Long replyId
) {
  public static CompletionEvent of(User user, Long replyId) {
    return new CompletionEvent(user, replyId);
  }
}
