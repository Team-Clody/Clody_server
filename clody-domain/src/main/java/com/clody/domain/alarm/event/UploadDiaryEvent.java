package com.clody.domain.alarm.event;

import com.clody.domain.diary.Diary;
import com.clody.domain.user.User;

public record UploadDiaryEvent(
    Diary savedDiary,
    User user
) {

}
