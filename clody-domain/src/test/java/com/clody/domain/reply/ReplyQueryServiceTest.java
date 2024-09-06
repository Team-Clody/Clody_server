package com.clody.domain.reply;

import static org.assertj.core.api.Assertions.assertThat;

import com.clody.domain.reply.dto.ReplyResponse;
import com.clody.domain.reply.repository.ReplyRepository;
import com.clody.domain.reply.service.ReplyQueryService;
import com.clody.domain.user.Platform;
import com.clody.domain.user.User;
import com.clody.support.security.util.JwtUtil;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReplyQueryServiceTest {

  @Mock
  private ReplyRepository replyRepository;

  @Mock
  private JwtUtil jwtUtil;

  @InjectMocks
  private ReplyQueryService replyQueryService;

  @Test
  @DisplayName("일기 내용이 null일 경우, isRead == false 반환")
  public void readReplyTest1(){
    //given
    User user = User.createNewUser("123", Platform.KAKAO,null, "JohnDoe","email");
    LocalDate date = LocalDate.now();
    Reply reply = Reply.createDynamicReply(user,date);

    //when
    reply.readReply();
    ReplyResponse response = ReplyResponse.from(reply);

    //then
    assertThat(response.isRead()).isFalse();
  }

  @Test
  @DisplayName("일기 내용이 채워진 경우, 일기를 처음 읽었을 경우 isRead == false")
  public void readFilledReply1(){
    //given
    User user = User.createNewUser("123", Platform.KAKAO,null, "JohnDoe","email");
    LocalDate date = LocalDate.now();
    Reply reply = Reply.createStaticReply(user,"content filled" , date);

    //when
    ReplyResponse response = ReplyResponse.from(reply);
    reply.readReply();

    //then
    assertThat(response.isRead()).isFalse();
  }

  @Test
  @DisplayName("일기 내용이 채워진 경우, 일기를 처음 읽었을 경우 isRead == false, 두번째 읽을 경우 True")
  public void readFilledReply2(){
    //given
    User user = User.createNewUser("123", Platform.KAKAO,null, "JohnDoe","email");
    LocalDate date = LocalDate.now();
    Reply reply = Reply.createStaticReply(user,"content filled" , date);

    //when
    ReplyResponse response = ReplyResponse.from(reply);
    reply.readReply();
    ReplyResponse afterReadResponse = ReplyResponse.from(reply);

    //then
    assertThat(response.isRead()).isFalse();
    assertThat(afterReadResponse.isRead()).isTrue();
  }

}
