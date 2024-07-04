package com.donkeys_today.server.domain.user;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

  private Long platformID;

  private Platform platform;

  @Column(name = "email")
  private String email;

  private String nickName;

  private boolean alarmAgreement;

  private boolean replyAgreement;

  private boolean is_deleted;

  @Builder
  public User(Long platformID, Platform platform, String email, String nickName,
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
