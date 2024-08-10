package com.clody.domain.diary.event;

import com.clody.domain.diary.Diary;
import java.util.List;

public record DefaultDiaryCreatedEvent (
    List<Diary> diaryList
) {

}
