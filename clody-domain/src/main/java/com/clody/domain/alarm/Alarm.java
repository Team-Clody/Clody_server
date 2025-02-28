package com.clody.domain.alarm;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.clody.domain.alarm.dto.UpdateAlarmCommand;
import com.clody.domain.user.User;
import com.clody.support.dto.type.ErrorType;
import com.clody.support.exception.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "alarm")
public class Alarm {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action= OnDeleteAction.CASCADE)
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
            .isDiaryAlarm(true)
            .isReplyAlarm(true)
            .time(LocalTime.of(21, 0))
            .build();
    }

    public void updateAlarm(UpdateAlarmCommand command){
        if(command.isDiaryAlarm()!=null){
            updateDiaryAlarm(command.isDiaryAlarm());
        }

        if(command.isReplyAlarm()!=null){
            updateReplyAlarm(command.isReplyAlarm());
        }

        if(command.time()!=null){
            updateTime(command.time());
        }

        if(command.fcmToken()!=null){
            updateFcmToken(command.fcmToken());
        }
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

    public void validateUserAgreedForReplyAlarm(){
        if(!isReplyAlarm){
            throw new BusinessException(ErrorType.USER_NOT_AGREED_FOR_REPLY_ALARM);
        }
    }

    public boolean checkUserAgreedForReplyAlarm(){
        return isReplyAlarm;
    }

    public void renewFcmToken(String fcmToken){
        this.fcmToken = fcmToken;
    }
}
