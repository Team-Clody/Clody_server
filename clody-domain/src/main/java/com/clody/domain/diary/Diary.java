package com.clody.domain.diary;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.clody.domain.user.User;
import com.clody.support.dto.type.ErrorType;
import com.clody.support.exception.NotFoundException;
import com.vane.badwordfiltering.BadWordFiltering;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
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

  private boolean containsProfanity;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  @Builder
  public Diary(User user, String content, boolean isDeleted, LocalDateTime createdAt,
      LocalDateTime updatedAt, boolean containsProfanity) {
    this.user = user;
    this.content = content;
    this.isDeleted = isDeleted;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.containsProfanity = containsProfanity;
  }

  public static Diary createDiary(User user, String content, boolean containsProfanity) {
    return Diary.builder()
        .user(user)
        .content(content)
        .isDeleted(false)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .containsProfanity(containsProfanity)
        .build();
  }

  public void deleteDiary() {
    if(!this.checkDiaryDeleted()){
      this.isDeleted = true;
    }
  }

  public boolean checkDiaryDeleted() {
    return this.isDeleted;
  }

  public static List<Diary> createDiaryList(User user, List<String> contents) {
    boolean containsProfanity = checkProfanity(contents);
    return contents.stream()
        .map(content -> createDiary(user, content, containsProfanity))
        .collect(Collectors.toUnmodifiableList());
  }

  public static boolean checkProfanity(List<String> contents) {
    BadWordFiltering filter = new BadWordFiltering();
    return contents.stream().anyMatch(filter::check);
  }

  public boolean matches(LocalDate localDate, Long userId){
    return this.createdAt.toLocalDate().equals(localDate) && this.user.getId().equals(userId);
  }

  public static Diary getLatestDiary(List<Diary> diaries) {
    return diaries.stream()
        .max(Comparator.comparing(Diary::getCreatedAt))
        .orElseThrow(() -> new NotFoundException(ErrorType.DIARY_MESSAGE_NOT_FOUND));
  }
}
