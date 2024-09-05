package com.clody.domain.reply;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.Objects;
import lombok.Getter;

@Embeddable
@Getter
public class ReplyInfo {

  private int version;

  @Enumerated(EnumType.STRING)
  private ReplyProcessStatus replyProcessStatus;


  public void updateStatus(ReplyProcessStatus replyProcessStatus) {
    if(this.replyProcessStatus == ReplyProcessStatus.PENDING){
      this.replyProcessStatus = replyProcessStatus;
    }
  }

  public boolean checkReadable(){
    return this.replyProcessStatus.equals(ReplyProcessStatus.SUCCEED);
  }

  public void incrementVersion() {
    this.version++;
  }

  public void isVersionMatch(int version){
    this.version = version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReplyInfo replyInfo = (ReplyInfo) o;
    return version == replyInfo.version && replyProcessStatus == replyInfo.replyProcessStatus;
  }

  @Override
  public int hashCode() {
    return Objects.hash(version, replyProcessStatus);
  }

  public ReplyInfo() {
  }

  public ReplyInfo(int version, ReplyProcessStatus replyProcessStatus) {
    this.version = version;
    this.replyProcessStatus = replyProcessStatus;
  }

  public static ReplyInfo createInitialReplyInfo(){
    return new ReplyInfo(0, ReplyProcessStatus.PENDING);
  }

  public static ReplyInfo createStaticReplyInfo(){
    return new ReplyInfo(0, ReplyProcessStatus.SUCCEED);
  }

  public ReplyInfo update(int version, ReplyProcessStatus replyProcessStatus){
    return new ReplyInfo(version, replyProcessStatus);
  }

  public boolean isDeleted(){
    return this.replyProcessStatus == ReplyProcessStatus.DELETED;
  }

  public ReplyInfo delete(){
    return new ReplyInfo(this.version, ReplyProcessStatus.DELETED);
  }
}
