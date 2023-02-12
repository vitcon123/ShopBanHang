package com.hoa.shopbanhang.application.utils;

import com.hoa.shopbanhang.adapter.web.base.RequestHeader;
/**
 * Utility class for Spring Security.
 */
public final class SecurityUtil {

  private SecurityUtil() {
  }

  public static String getCurrentUserLogin() {
    RequestHeader requestHeader = BeanUtil.getBean(RequestHeader.class);
    return requestHeader.getUser();
  }

}
