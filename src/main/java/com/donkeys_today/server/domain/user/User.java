package com.donkeys_today.server.domain.user;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private Long platformID;

  @Column(name = "email")
  private String email;

  @Column(name = "username")
  private String userName;

  private String nickName;

  private boolean alarmAgreement;

  private boolean replyAgreement;

  private boolean is_deleted;

  @Builder
  public User(String email, String userName, String nickName, boolean alarmAgreement,
      boolean replyAgreement, boolean is_deleted) {
    this.email = email;
    this.userName = userName;
    this.nickName = nickName;
    this.alarmAgreement = alarmAgreement;
    this.replyAgreement = replyAgreement;
    this.is_deleted = is_deleted;
  }
}
