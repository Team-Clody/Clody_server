package com.donkeys_today.server.domain.diary;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.support.auditing.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "diaries")
public class Diary extends BaseEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "content")
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  private boolean isDeleted;

  @Builder
  public Diary(User user, String content, boolean isDeleted) {
    this.user = user;
    this.content = content;
    this.isDeleted = isDeleted;
  }

  public static Diary createDiary(User user, String content) {
    return Diary.builder()
        .user(user)
        .content(content)
        .isDeleted(false)
        .build();
  }

  public void deleteDiary(){
    this.isDeleted = true;
  }
}
