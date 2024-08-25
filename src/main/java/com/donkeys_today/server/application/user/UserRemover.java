package com.donkeys_today.server.application.user;

import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.infrastructure.user.UserRepository;
import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.BusinessException;
import com.donkeys_today.server.support.exception.auth.UserDeleteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserRemover {

    private final UserRepository userRepository;

    @Transactional
    public User deleteUserById(Long userId) {
        return userRepository.findById(userId).map(user -> {
            userRepository.delete(user);
            return user;
        }).orElseThrow(() -> new UserDeleteException(ErrorType.NOTFOUND_USER_ERROR));
    }
}
