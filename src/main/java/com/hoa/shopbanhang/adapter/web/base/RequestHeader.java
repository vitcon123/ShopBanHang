package com.hoa.shopbanhang.adapter.web.base;

import com.hoa.shopbanhang.application.constants.AuthorityConstant;
import com.hoa.shopbanhang.application.constants.HeaderConstant;
import com.hoa.shopbanhang.application.constants.UserMessageConstant;
import com.hoa.shopbanhang.configs.exceptions.VsException;
import com.nimbusds.jwt.SignedJWT;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Map;

@Component("WebTransferRequestHeader")
public class RequestHeader {
  private final HttpServletRequest httpServletRequest;

  public RequestHeader(HttpServletRequest httpServletRequest) {
    this.httpServletRequest = httpServletRequest;
  }

//  /**
//   * @return String
//   */
//  public String getOrganizationId() {
//    String orgId = httpServletRequest.getHeader(HeaderConstant.X_ORGANIZATION_ID);
//    if (StringUtils.isEmpty(orgId)) {
//      throw new InvalidException(UserMessageConstant.ERR_EXCEPTION_ACCESS_SYSTEM, DevMessageConstant.Header
//      .ORGANIZATION_REQUIRED);
//    }
//    return orgId;
//  }

  /**
   * @return String
   */
  public String getUser() {
    String bearerToken = httpServletRequest.getHeader(HeaderConstant.AUTHORIZATION);
    if (StringUtils.isEmpty(bearerToken)) {
      return AuthorityConstant.ANONYMOUS_USER;
    }
    String token = bearerToken.substring(7);
    try {
      SignedJWT decodedJWT = SignedJWT.parse(token);
      Map<String, Object> payload = decodedJWT.getPayload().toJSONObject();
      return (String) payload.get(AuthorityConstant.CLAIM_SUB);
    } catch (ParseException e) {
      throw new VsException(UserMessageConstant.ERR_EXCEPTION_ACCESS_SYSTEM, e.getMessage());
    }
  }

}
