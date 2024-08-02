package com.clody.support.security.util;

import com.clody.support.dto.type.ErrorType;
import com.clody.support.exception.UnauthorizedException;
import com.clody.support.security.auth.UserAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtUtil {

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
