package com.clody.clodyapi.diary.controller.dto.request;

import java.util.ArrayList;

public record DiaryRequest(
    String date,
    ArrayList<String> content
) {

}
