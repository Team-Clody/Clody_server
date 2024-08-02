package com.clody.support.security.auth;

import com.clody.support.dto.ApiResponse;
import com.clody.support.dto.type.ErrorType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper ;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
      handleException(response);
  }

  private void handleException(HttpServletResponse response) throws IOException {
    setResponse(response, HttpStatus.UNAUTHORIZED, ErrorType.UNAUTHORIZED);
  }

  private void setResponse(HttpServletResponse response, HttpStatus httpStatus, ErrorType errorType)
      throws IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.setStatus(httpStatus.value());
    PrintWriter writer = response.getWriter();
    writer.write(objectMapper.writeValueAsString(ApiResponse.error(errorType)));
  }
}
