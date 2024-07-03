package com.donkeys_today.server.support.security.filter;

import static com.donkeys_today.server.support.security.auth.UserAuthentication.createUserAuthentication;

import com.donkeys_today.server.common.constants.Constants;
import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.UnauthorizedException;
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
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    final String accessToken = getAccessToken(request);
    jwtProvider.validateAccessToken(accessToken);
    doAuthentication(request, jwtProvider.getUserIdFromJwtSubject(accessToken));
    filterChain.doFilter(request, response);
  }

  private String getAccessToken(HttpServletRequest request) {
    String accessToken = request.getHeader(Constants.AUTHORIZATION);
    if(StringUtils.hasText(accessToken) && accessToken.startsWith(Constants.BEARER)) {
      return accessToken.substring(Constants.BEARER.length());
    }
    throw new UnauthorizedException(ErrorType.UNAUTHORIZED);
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

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException{
    String requestUri = request.getRequestURI();
    return WhiteListConstants.FILTER_WHITE_LIST.stream().anyMatch(requestUri::startsWith);
  }
}
