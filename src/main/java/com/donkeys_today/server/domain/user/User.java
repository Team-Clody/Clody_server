package com.donkeys_today.server.domain.user;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String platformID;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    @Column(name = "email")
    private String email;

    private String nickName;

    private boolean alarmAgreement;

    private boolean replyAgreement;

    private boolean is_deleted;

    @Builder
    public User(String platformID, Platform platform, String email, String nickName,
                boolean alarmAgreement, boolean replyAgreement, boolean is_deleted) {
        this.platformID = platformID;
        this.platform = platform;
        this.email = email;
        this.nickName = nickName;
        this.alarmAgreement = alarmAgreement;
        this.replyAgreement = replyAgreement;
        this.is_deleted = is_deleted;
    }

    public void updateUserAlarmAgreement(boolean alarmAgreement) {
        this.alarmAgreement = alarmAgreement;
    }

}
