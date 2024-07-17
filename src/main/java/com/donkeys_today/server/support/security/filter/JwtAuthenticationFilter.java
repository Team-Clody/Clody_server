package com.donkeys_today.server.support.security.filter;

import static com.donkeys_today.server.support.security.auth.UserAuthentication.createUserAuthentication;

import com.donkeys_today.server.common.constants.Constants;
import com.donkeys_today.server.support.jwt.JwtProvider;
import com.donkeys_today.server.support.security.auth.UserAuthentication;
import com.donkeys_today.server.support.security.config.WhiteListConstants;
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

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    if (request.getHeader(Constants.AUTHORIZATION) == null) {
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
    String TokenWithBearerOrNot = request.getHeader(Constants.AUTHORIZATION);
    jwtProvider.validateTokenStartsWithBearer(TokenWithBearerOrNot);
  }

  private String getAccessToken(HttpServletRequest request) {
    String accessToken = request.getHeader(Constants.AUTHORIZATION);
    return accessToken.substring(Constants.BEARER.length());

  }

  private void doAuthentication(HttpServletRequest request, Long userId) {
    UserAuthentication authentication = createUserAuthentication(userId);
    createAndSetAuthenticationDetails(request , authentication);
    SecurityContext securityContext = SecurityContextHolder.getContext();
    securityContext.setAuthentication(authentication);
  }

  private static void createAndSetAuthenticationDetails(HttpServletRequest request, UserAuthentication authentication){
    //Authentication에 Detail 추가 (IP + Session ID)
    WebAuthenticationDetailsSource webAuthenticationDetailsSource = new WebAuthenticationDetailsSource();
    WebAuthenticationDetails webAuthenticationDetails = webAuthenticationDetailsSource.buildDetails(request);
    authentication.setDetails(webAuthenticationDetails);
  }

  // signup login 은 그냥 지나가야함.
  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
  String path = request.getRequestURI();
  for (String whitePath : WhiteListConstants.FILTER_WHITE_LIST) {
    if (path.equals(whitePath)) {
      return true;
    }
  }
  return false;
  }
}
