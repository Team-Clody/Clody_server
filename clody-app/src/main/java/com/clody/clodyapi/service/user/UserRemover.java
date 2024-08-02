package com.clody.clodyapi.service.user;

import com.clody.support.dto.type.ErrorType;
import com.clody.support.exception.BusinessException;
import com.clody.domain.user.User;
import com.clody.domain.user.repository.UserRepository;
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
        }).orElseThrow(() -> new BusinessException(ErrorType.NOTFOUND_USER_ERROR));
    }
}
