package com.clody.clodyapi.presentation.diary.dto.request;

import java.util.ArrayList;

public record DiaryRequest(
    String date,
    ArrayList<String> content
) {

}
