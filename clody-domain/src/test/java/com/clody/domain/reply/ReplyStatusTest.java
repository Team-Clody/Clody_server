package com.clody.domain.reply;

import static org.assertj.core.api.Assertions.assertThat;

import com.clody.domain.user.Platform;
import com.clody.domain.user.User;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReplyStatusTest {


  @Test
  @DisplayName("답장이 준비되지 않았을 경우, isRead == false를 반환한다.")
  public void testReadIfUnready(){
    //given
    User user = User.createNewUser("1L", Platform.KAKAO, null, "hyunw9", "email");
    LocalDate today = LocalDate.now();
    Reply dynamicReply = Reply.createDynamicReply(user, today);
    Reply staticReply = Reply.createStaticReply(user,"static Reply" ,today);
    Reply firstReply = Reply.createFastDynamicReply(user, today);

    //when
    dynamicReply.readReply();
    staticReply.readReply();
    firstReply.readReply();

    //then
    assertThat(dynamicReply.checkUserReadReply()).isEqualTo(false);
    assertThat(staticReply.checkUserReadReply()).isEqualTo(true);
    assertThat(firstReply.checkUserReadReply()).isEqualTo(false);

  }

  @Test
  @DisplayName("답장이 준비되지 않은 상태에서 status 반환시, UNREAD를 반환한다. ")
  public void test1(){

    //given
    User user = User.createNewUser("1L", Platform.KAKAO, null, "hyunw9", "email");
    LocalDate today = LocalDate.now();
    Reply dynamicReply = Reply.createDynamicReply(user, today);
    Reply staticReply = Reply.createStaticReply(user,"static Reply" ,today);
    Reply firstReply = Reply.createFastDynamicReply(user, today);

    //when
    UserReplyReadStatus dynamicReplyReadStatus = dynamicReply.processReadStatus(dynamicReply);
    UserReplyReadStatus staticReplyReadStatus = staticReply.processReadStatus(staticReply);
    UserReplyReadStatus firstReplyReadStatus = firstReply.processReadStatus(firstReply);

    //then
    assertThat(dynamicReplyReadStatus).isEqualTo(UserReplyReadStatus.UNREADY);
    assertThat(staticReplyReadStatus).isEqualTo(UserReplyReadStatus.UNREADY);
    assertThat(firstReplyReadStatus).isEqualTo(UserReplyReadStatus.UNREADY);
  }

  @Test
  @DisplayName("답장이 준비되었지만 읽지 않은 경우, isRead == False를 반환한다.")
  public void test2(){
    //given
    User user = User.createNewUser("1L", Platform.KAKAO, null, "hyunw9", "email");
    LocalDate today = LocalDate.now();
    Reply dynamicReply = Reply.createDynamicReply(user, today);
    Reply staticReply = Reply.createStaticReply(user,"static Reply" ,today);
    Reply firstReply = Reply.createFastDynamicReply(user, today);

    //when
    boolean dynamicIsRead = dynamicReply.checkUserReadReply();
    boolean staticIsRead = staticReply.checkUserReadReply();
    boolean firstIsRead = firstReply.checkUserReadReply();

    //then
    assertThat(dynamicIsRead).isEqualTo(false);
    assertThat(staticIsRead).isEqualTo(false);
    assertThat(firstIsRead).isEqualTo(false);
  }

  @Test
  @DisplayName("답장이 준비되었고 읽은 경우, isRead == True 반환한다.")
  public void test3(){
    //given
    User user = User.createNewUser("1L", Platform.KAKAO, null, "hyunw9", "email");
    LocalDate today = LocalDate.now();
    Reply dynamicReply = Reply.createDynamicReply(user, today);
    Reply staticReply = Reply.createStaticReply(user,"static Reply" ,today);
    Reply firstReply = Reply.createFastDynamicReply(user, today);

    //when
    dynamicReply.insertContentFromRody("content",1);
    staticReply.insertContentFromRody("content",1);
    firstReply.insertContentFromRody("content",1);

    dynamicReply.readReply();
    staticReply.readReply();
    firstReply.readReply();

    boolean dynamicIsRead = dynamicReply.checkUserReadReply();
    boolean staticIsRead = staticReply.checkUserReadReply();
    boolean firstIsRead = firstReply.checkUserReadReply();

    //then
    assertThat(dynamicIsRead).isEqualTo(true);
    assertThat(staticIsRead).isEqualTo(true);
    assertThat(firstIsRead).isEqualTo(true);
  }
}
