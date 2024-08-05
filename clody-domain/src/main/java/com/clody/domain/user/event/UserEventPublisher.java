package com.clody.domain.user.event;

public interface UserEventPublisher {

    void publishUserCreatedEvent(UserCreatedEvent userCreatedEvent);

}
