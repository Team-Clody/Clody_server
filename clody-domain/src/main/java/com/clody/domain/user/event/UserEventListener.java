package com.clody.domain.user.event;

import com.clody.domain.diary.event.UserDiaryWrittenEvent;
import com.clody.domain.user.User;
import com.clody.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventListener {

  private final UserRepository userRepository;

  @EventListener
  public void makeUserDiaryWritten(UserDiaryWrittenEvent event){
   User user = event.user();
   user.makeDiaryWritten();
   userRepository.save(user);
  }
}
