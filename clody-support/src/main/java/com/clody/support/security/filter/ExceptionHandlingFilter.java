package com.clody.support.security.filter;

import com.clody.support.dto.ApiResponse;
import com.clody.support.dto.type.ErrorType;
import com.clody.support.exception.UnauthorizedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class ExceptionHandlingFilter extends OncePerRequestFilter {

  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (UnauthorizedException ex) {
      handleUnauthorizedException(response, ex);
    } catch (Exception e) {
      handleException(response, e);
    }
  }

  private void handleUnauthorizedException(HttpServletResponse response, Exception e)
      throws IOException {
    UnauthorizedException ue = (UnauthorizedException) e;
    ErrorType errorType = ue.getErrorType();
    int status = errorType.getStatus();
    setResponse(response, status, errorType);
  }

  private void handleException(HttpServletResponse response, Exception e) throws IOException {
    log.error("------Exception Handler :",e);
    setResponse(response, HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorType.INTERNAL_SERVER_ERROR);
  }
  private void setResponse(HttpServletResponse response, int status, ErrorType errorType)
      throws IOException {
    response.setContentType("application/json");
    response.setStatus(status);
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    out.write(mapper.writeValueAsString(ApiResponse.error(errorType)));
  }
}
