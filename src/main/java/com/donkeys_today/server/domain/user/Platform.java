package com.donkeys_today.server.domain.user;

import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.InternalServerException;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Platform {

    KAKAO("kakao"),
    GOOGLE("google"),
    APPLE("apple");

    private final String name;

    public static Platform fromString(String name) {
        return Arrays.stream(Platform.values())
                .filter(p -> p.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new InternalServerException(ErrorType.INTERNAL_SERVER_ERROR));
    }
}
