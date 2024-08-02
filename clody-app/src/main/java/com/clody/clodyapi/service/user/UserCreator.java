package com.clody.clodyapi.service.user;

import com.clody.domain.user.User;
import com.clody.domain.user.repository.UserRepository;
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
