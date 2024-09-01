package com.clody.domain.diary.dto.response;

import java.util.List;

public record UserGetResponse(
        int totalCloverCount,
        List<DiaryDayInfo> diaries
) {

}

