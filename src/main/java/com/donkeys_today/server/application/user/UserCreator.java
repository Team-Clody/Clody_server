package com.donkeys_today.server.application.user;

import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.infrastructure.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreator {

    private final UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
