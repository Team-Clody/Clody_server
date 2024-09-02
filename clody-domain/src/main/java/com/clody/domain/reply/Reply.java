package com.clody.domain.reply;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.clody.domain.base.BaseEntity;
import com.clody.domain.user.User;
import com.clody.support.dto.type.ErrorType;
import com.clody.support.exception.BusinessException;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "replies", indexes = @Index(name = "idx_diary_created_date", columnList = "diary_created_date"))
public class Reply extends BaseEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "content", columnDefinition = "TEXT")
  private String content;

  @Column(name = "is_read")
  private Boolean is_read;

  @Column(name = "diary_created_date")
  private LocalDate diaryCreatedDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "user_id")
  private User user;

  @Enumerated(EnumType.STRING)
  private ReplyType replyType;

  @Embedded
  private ReplyInfo replyInfo;

  @Builder
  public Reply(String content, Boolean is_read, LocalDate diaryCreatedDate, User user,
      ReplyType replyType, ReplyInfo replyInfo) {
    this.content = content;
    this.is_read = is_read;
    this.diaryCreatedDate = diaryCreatedDate;
    this.user = user;
    this.replyType = replyType;
    this.replyInfo = replyInfo;
  }

  public static Reply createStaticReply(User user, String content, LocalDate createdDate) {
    ReplyInfo initialInfo = ReplyInfo.createStaticReplyInfo();
    return new Reply(content, false, createdDate, user, ReplyType.STATIC, initialInfo);
  }

  public static Reply createDynamicReply(User user, LocalDate createdDate) {
    ReplyInfo initialInfo = ReplyInfo.createInitialReplyInfo();
    return new Reply(null, false, createdDate, user, ReplyType.DYNAMIC, initialInfo);
  }

  public static Reply createFastDynamicReply(User user, LocalDate createdDate) {
    ReplyInfo initialInfo = ReplyInfo.createInitialReplyInfo();
    return new Reply(null, false, createdDate, user, ReplyType.FIRST, initialInfo);
  }

  public void readReply() {
    this.is_read = true;
  }

  public boolean isNotRead() {
    return !this.is_read;
  }

  public boolean isRead() {
    return this.is_read;
  }

  public Integer getVersion() {
    return this.replyInfo.getVersion();
  }

  private ReplyProcessStatus getProcessStatus() {
    return this.replyInfo.getReplyProcessStatus();
  }

  public void insertContentFromRody(String content, Integer messageVersion) {
    if (messageVersion < getVersion() && getProcessStatus()== ReplyProcessStatus.UPDATED) {
      throw new BusinessException(ErrorType.USER_UPDATED_DIARY);
    }
    // 내용 업데이트
    this.content = content;
    this.replyInfo = replyInfo.update(messageVersion, ReplyProcessStatus.SUCCEED);
  }

  public boolean checkReplyDeleted() {
    return this.replyInfo.isDeleted();
  }

  public void delete(){
    this.replyInfo = replyInfo.delete();
  }

  public boolean checkIfFirstReply(){
    return this.replyType == ReplyType.FIRST;
  }
}
