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
  private long id;

  @Column(name = "username")
  private String userName;

  @Column(name = "phone_num")
  private String phoneNum;

  @Column(name = "email")
  private String email;

  @Builder
  public User(String userName, String phoneNum, String email) {
    this.userName = userName;
    this.phoneNum = phoneNum;
    this.email = email;
  }

}
