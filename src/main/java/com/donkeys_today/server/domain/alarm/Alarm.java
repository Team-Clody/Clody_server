package com.donkeys_today.server.domain.alarm;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.donkeys_today.server.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "alarm")
public class Alarm {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "time")
    private LocalDateTime time;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String fcmToken;

    @Column(name = "time")
    private LocalTime time;

    private boolean isDiaryAlarm;

    private boolean isReplyAlarm;

    @Builder
    public Alarm(User user, String fcmToken, LocalTime time, boolean isDiaryAlarm,
        boolean isReplyAlarm) {
        this.user = user;
        this.fcmToken = fcmToken;
        this.time = time;
        this.isDiaryAlarm = isDiaryAlarm;
        this.isReplyAlarm = isReplyAlarm;
    }

    public static Alarm createInitailDiaryAlarm(User user){
        return Alarm.builder()
            .user(user)
            .isDiaryAlarm(false)
            .isReplyAlarm(false)
            .build();
    }

    public void updateDiaryAlarm(boolean isDiaryAlarm){
        this.isDiaryAlarm = isDiaryAlarm;
    }

    public void updateReplyAlarm(boolean isReplyAlarm){
        this.isReplyAlarm = isReplyAlarm;
    }

    public void updateTime(LocalTime time){
        this.time = time;
    }

    public void updateFcmToken(String fcmToken){
        this.fcmToken = fcmToken;
    }

}
