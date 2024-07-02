package com.donkeys_today.server.domain.user;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.donkeys_today.server.domain.alarm.Alarm;
import com.donkeys_today.server.domain.diary.Diary;
import com.donkeys_today.server.domain.reply.Reply;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, name = "email")
    private String email;

    @Column(nullable = false, name = "nickname")
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private RoleType roleType;

    @Column(name = "provider")
    private ProviderType providerType;

    @Column(name = "alarm_agreement")
    private Boolean alarm_agreemente;

    @Column(name = "reply_agreement")
    private Boolean reply_agreemente;

    @Column(name = "is_deleted")
    private Boolean is_deleted;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarm_id")
    private Alarm alarm;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Diary> diaries = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Reply> replies = new ArrayList<>();

    @Builder

    public User(String email, String nickname, RoleType roleType, ProviderType providerType, Boolean alarm_agreemente,
                Boolean reply_agreemente, Boolean is_deleted, Alarm alarm) {
        this.email = email;
        this.nickname = nickname;
        this.roleType = roleType;
        this.providerType = providerType;
        this.alarm_agreemente = alarm_agreemente;
        this.reply_agreemente = reply_agreemente;
        this.is_deleted = is_deleted;
        this.alarm = alarm;
    }
}
