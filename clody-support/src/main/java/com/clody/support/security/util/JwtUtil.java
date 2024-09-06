package com.clody.support.security.util;

import com.clody.support.dto.type.ErrorType;
import com.clody.support.exception.UnauthorizedException;
import com.clody.support.security.auth.UserAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtUtil {

  //Todo Static -> DI 를 이용한 방식으로 리팩토링 필요. (테스트 용이성)
  public static Long getLoginMemberId() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.isAuthenticated()
        && authentication instanceof UserAuthentication) {

      Object principal = authentication.getPrincipal();
      System.out.println((Long) principal);
      if (principal instanceof Long) {
        return (Long) principal;
      } else if (principal instanceof String) {
        return Long.parseLong((String) principal);
      }
    }
    throw new UnauthorizedException(ErrorType.UNAUTHORIZED);
  }
}
