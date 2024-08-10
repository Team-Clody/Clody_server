package com.clody.domain.user;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.clody.domain.base.BaseEntity;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
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

@Entity
@NoArgsConstructor
@Table(name = "users")
@Access(AccessType.FIELD)
@Getter
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private String platformID;

  @Enumerated(EnumType.STRING)
  private Platform platform;

  @Column(name = "email")
  private String email;

  private String nickName;

  private boolean is_deleted;

  private boolean hasWrittenDiary;

  @Builder
  public User(String platformID, Platform platform, String email, String nickName,
      boolean is_deleted) {
    this.platformID = platformID;
    this.platform = platform;
    this.email = email;
    this.nickName = nickName;
    this.is_deleted = is_deleted;
  }

  public void updateUserName(String newName) {
    this.nickName = newName;
  }

  public void makeDiaryWritten(){
    if(!hasWrittenDiary){
      this.hasWrittenDiary = true;
    }
  }

  public boolean hasNoDiary() {
    return !hasWrittenDiary;
  }

  public static User createNewUser(String platformId, Platform platform, String email, String nickName) {
    return User.builder()
        .platformID(platformId)
        .platform(platform)
        .email(email)
        .nickName(nickName)
        .is_deleted(false)
        .build();
  }
}
