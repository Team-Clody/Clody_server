package com.donkeys_today.server.application.auth;

import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.UnauthorizedException;
import com.donkeys_today.server.support.security.auth.UserAuthentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtUtil {

  public static Long getLoginMemberId() {
    UserAuthentication authentication = (UserAuthentication) SecurityContextHolder.getContext()
        .getAuthentication();
    if (authentication != null) {
      return (Long) authentication.getPrincipal();
    }
    throw new UnauthorizedException(ErrorType.UNAUTHORIZED);
  }
}
