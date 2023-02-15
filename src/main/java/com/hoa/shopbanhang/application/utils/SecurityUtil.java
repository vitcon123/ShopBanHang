package com.hoa.shopbanhang.application.utils;

import com.hoa.shopbanhang.adapter.web.base.RequestHeader;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtil {

  private SecurityUtil() {
  }

  public static String getCurrentUserLogin() {
//    RequestHeader requestHeader = BeanUtil.getBean(RequestHeader.class);
//    return requestHeader.getUser();
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username = "";
    if (principal instanceof UserDetails) {
      username = ((UserDetails) principal).getUsername();
    } else {
      username = principal.toString();
    }
    return username;
  }

}
