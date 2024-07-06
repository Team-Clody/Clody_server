//package com.donkeys_today.server.docs;
//
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.restassured.module.mockmvc.RestAssuredMockMvc;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.restdocs.RestDocumentationContextProvider;
//import org.springframework.restdocs.RestDocumentationExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//@ExtendWith(RestDocumentationExtension.class)
//public abstract class RestDocsSupport {
//
//  protected MockMvc mockMvc;
//  protected ObjectMapper objectMapper = new ObjectMapper();
//
//  @BeforeEach
//  public void setUp(RestDocumentationContextProvider provider) throws Exception {
//    this.mockMvc = MockMvcBuilders.standaloneSetup(initController())
//        .apply(documentationConfiguration(provider))
//        .build();
//    RestAssuredMockMvc.mockMvc(this.mockMvc);
//  }
//
//  protected abstract Object initController() throws Exception;
//}
