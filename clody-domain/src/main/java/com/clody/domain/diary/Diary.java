package com.clody.domain.diary;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.clody.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "diaries")
public class Diary {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "content")
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "user_id")
  private User user;

  private boolean isDeleted;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  @Builder
  public Diary(User user, String content, boolean isDeleted, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.user = user;
    this.content = content;
    this.isDeleted = isDeleted;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public void deleteDiary() {
    this.isDeleted = true;
  }

  public boolean checkDiaryDeleted(){
    return this.isDeleted;
  }
}
