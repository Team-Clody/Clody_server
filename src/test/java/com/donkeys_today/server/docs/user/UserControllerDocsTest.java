//
//package com.donkeys_today.server.docs.user;
//
//import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
//import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
//import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes.NUMBER;
//import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes.STRING;
//import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.mock;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
//import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
//import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
//
//import com.donkeys_today.server.application.user.UserService;
//import com.donkeys_today.server.application.user.dto.request.UserSignUpRequest;
//import com.donkeys_today.server.application.user.dto.response.UserResponse;
//import com.donkeys_today.server.application.user.dto.response.UserSignUpResponse;
//import com.donkeys_today.server.docs.RestDocsSupport;
//import com.donkeys_today.server.presentation.user.UserController;
//import com.epages.restdocs.apispec.ResourceSnippetParameters;
//import com.epages.restdocs.apispec.Schema;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//public class UserControllerDocsTest extends RestDocsSupport {
//
//  private final UserService userService = mock(UserService.class);
//
//  @Override
//  protected Object initController() throws Exception {
//    return new UserController(userService);
//  }
//
//  @DisplayName("User 조회 API Test")
//  @Test
//  void 유저_조회_테스트() throws Exception {
//
//    var 유저_요청_응답 = new UserResponse(1L, "홍길동", "010-9090-9090", "rkd@gm.com");
//
//    given(userService.getUser(anyLong())).willReturn(유저_요청_응답);
//
//    given()
//        .param("id", "1")
//        .when()
//        .get("http://localhost:8080/api/v1/user")
//        .then()
//        .statusCode(200)
//        .and()
//        .apply(document("findUser",
//            preprocessRequest(prettyPrint()),
//            preprocessResponse(prettyPrint()),
//            resource(ResourceSnippetParameters.builder()
//                .tag("User API")
//                .summary("유저 관련 API")
//                .queryParameters(
//                    parameterWithName("id").description("유저 ID")
//                ).responseFields(
//                    fieldWithPath("status").type(NUMBER).description("상태 코드"),
//                    fieldWithPath("message").type(STRING).description("상태 메세지"),
//                    fieldWithPath("data.id").type(NUMBER).description("유저 ID"),
//                    fieldWithPath("data.userName").type(STRING).description("사용자 이름"),
//                    fieldWithPath("data.phoneNum").type(STRING).description("사용자 전화번호"),
//                    fieldWithPath("data.email").type(STRING).description("사용자 이메일"))
//                .responseSchema(Schema.schema("UserResponse"))
//                .build()
//            )));
//
//  }
//  @Test
//  public void 유저_생성_테스트(){
//    var 유저_가입_요청 = new UserSignUpRequest("홍길동", "010-9090-9090", "rkd@gm.com");
//    var 유저_가입_응답 = new UserSignUpResponse(1L, "홍길동", "010-9090-9090", "rkd@gm.com");
//
//    given(userService.signUp(any(UserSignUpRequest.class))).willReturn(유저_가입_응답);
//
//    given()
//        .body(유저_가입_요청)
//        .contentType("application/json")
//        .when()
//        .post("http://localhost:8080/api/v1/user/signUp")
//        .then()
//        .statusCode(201)
//        .and()
//        .apply(document("signUpUser",
//            preprocessRequest(prettyPrint()),
//            preprocessResponse(prettyPrint()),
//            resource(ResourceSnippetParameters.builder()
//                .tag("User API")
//                .summary("유저 관련 API")
//                    .requestFields(
//                        fieldWithPath("userName").type(STRING).description("사용자 이름"),
//                        fieldWithPath("phoneNum").type(STRING).description("전화번호"),
//                        fieldWithPath("email").type(STRING).description("이메일")
//                    ).responseFields(
//                        fieldWithPath("status").type(NUMBER).description("상태 코드"),
//                        fieldWithPath("message").type(STRING).description("상태 메세지"),
//                        fieldWithPath("data.id").type(NUMBER).description("유저 ID"),
//                        fieldWithPath("data.userName").type(STRING).description("사용자 이름"),
//                        fieldWithPath("data.phoneNum").type(STRING).description("전화번호"),
//                        fieldWithPath("data.email").type(STRING).description("이메일"))
//                    .responseSchema(Schema.schema("UserSignUpResponse"))
//                    .build()
//                )));
//  }
//}
