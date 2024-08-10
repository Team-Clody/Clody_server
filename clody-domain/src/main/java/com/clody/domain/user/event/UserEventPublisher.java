package com.clody.domain.user.event;

import com.clody.domain.diary.event.UserDiaryWrittenEvent;

public interface UserEventPublisher {

    void publishUserCreatedEvent(UserCreatedEvent userCreatedEvent);

    void publishUserStatusChangedEvent(UserDiaryWrittenEvent userDiaryWrittenEvent);

}
