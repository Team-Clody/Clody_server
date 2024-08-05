package com.clody.support.security.filter;

import static com.clody.support.constants.HeaderConstants.AUTHORIZATION;
import static com.clody.support.constants.HeaderConstants.BEARER;
import static com.clody.support.security.auth.UserAuthentication.createUserAuthentication;

import com.clody.support.constants.WhiteListConstants;
import com.clody.support.jwt.JwtProvider;
import com.clody.support.security.auth.UserAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;

  private static void createAndSetAuthenticationDetails(HttpServletRequest request,
      UserAuthentication authentication) {
    //Authentication에 Detail 추가 (IP + Session ID)
    WebAuthenticationDetailsSource webAuthenticationDetailsSource = new WebAuthenticationDetailsSource();
    WebAuthenticationDetails webAuthenticationDetails = webAuthenticationDetailsSource.buildDetails(
        request);
    authentication.setDetails(webAuthenticationDetails);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    if (request.getHeader(AUTHORIZATION) == null) {
      filterChain.doFilter(request, response);
      return;
    }

    validateAuthorizationHeader(request);
    String accessToken = getAccessToken(request);
    jwtProvider.validateAccessToken(accessToken);
    doAuthentication(request, jwtProvider.getUserIdFromJwtSubject(accessToken));
    filterChain.doFilter(request, response);
  }

  private void validateAuthorizationHeader(HttpServletRequest request) {
    String TokenWithBearerOrNot = request.getHeader(AUTHORIZATION);
    jwtProvider.validateTokenStartsWithBearer(TokenWithBearerOrNot);
  }

  private String getAccessToken(HttpServletRequest request) {
    String accessToken = request.getHeader(AUTHORIZATION);
    return accessToken.substring(BEARER.length());

  }

  private void doAuthentication(HttpServletRequest request, Long userId) {
    UserAuthentication authentication = createUserAuthentication(userId);
    createAndSetAuthenticationDetails(request, authentication);
    SecurityContext securityContext = SecurityContextHolder.getContext();
    securityContext.setAuthentication(authentication);
  }

  // signup login 은 그냥 지나가야함.
  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getRequestURI();
    for (String whitePath : WhiteListConstants.FILTER_WHITE_LIST) {
      if (whitePath.equals(path)) {
        return true;
      }
    }
    return false;
  }
}
