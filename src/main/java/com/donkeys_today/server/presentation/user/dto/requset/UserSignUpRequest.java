package com.donkeys_today.server.presentation.user.dto.requset;

import java.time.LocalTime;

public record UserSignUpRequest(
    String platform,
    boolean alarmAgreement,
    LocalTime alarmTime
) {
}
