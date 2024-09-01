package com.clody.domain.alarm.strategy;

import java.util.concurrent.TimeUnit;

public record RelativeTime(
    long amount,
    TimeUnit timeUnit
) {

}
