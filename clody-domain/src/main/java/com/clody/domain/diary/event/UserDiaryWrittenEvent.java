package com.clody.domain.diary.event;

import com.clody.domain.user.User;

public record UserDiaryWrittenEvent(
    User user
) {
}
