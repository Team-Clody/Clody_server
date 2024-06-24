package com.donkeys_today.server.docs.user;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes.NUMBER;
import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes.STRING;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.donkeys_today.server.application.user.UserService;
import com.donkeys_today.server.application.user.dto.request.UserSignUpRequest;
import com.donkeys_today.server.application.user.dto.response.UserResponse;
import com.donkeys_today.server.docs.RestDocsSupport;
import com.donkeys_today.server.presentation.user.UserController;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

public class UserControllerDocsTest extends RestDocsSupport {

  private final UserService userService = mock(UserService.class);

  @Override
  protected Object initController() throws Exception {
    return new UserController(userService);
  }

  @DisplayName("User 생성 API Test")
  @Test
  void 유저_생성_테스트() throws Exception {
    //when , then
    UserSignUpRequest request = new UserSignUpRequest("홍길동", "010-9090-9090", "rkd@gm.com");
    UserResponse response = new UserResponse(1L, "홍길동", "010-9090-9090", "rkd@gm.com");

    given(userService.getUser(anyLong()))
        .willReturn(response);

    mockMvc.perform(
            RestDocumentationRequestBuilders.get("http://localhost:8080/api/v1/user")
                .param("id", "1")
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("findUser",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            resource(ResourceSnippetParameters.builder()
                .tag("User API")
                .summary("유저 관련 API")
                .queryParameters(
                    parameterWithName("id").description("유저 ID")
                ).responseFields(
                    fieldWithPath("status").type(NUMBER).description("상태 코드"),
                    fieldWithPath("message").type(STRING).description("상태 메세지"),
                    fieldWithPath("data.id").type(NUMBER).description("유저 ID"),
                    fieldWithPath("data.userName").type(STRING).description("사용자 이름"),
                    fieldWithPath("data.phoneNum").type(STRING).description("사용자 전화번호"),
                    fieldWithPath("data.email").type(STRING).description("사용자 이메일"))
//                .requestSchema(Schema.schema("FormParameter"))
                    .responseSchema(Schema.schema("UserResponse"))
                    .build()
            )));
  }
}
