package com.hoa.shopbanhang.application.listens;

import com.hoa.shopbanhang.application.constants.EmailConstant;
import com.hoa.shopbanhang.application.events.SignUpEvent;
import com.hoa.shopbanhang.application.services.ITokenService;
import com.hoa.shopbanhang.application.utils.SendMailUtil;
import com.hoa.shopbanhang.application.utils.UrlUtil;
import com.hoa.shopbanhang.configs.exceptions.NotFoundException;
import com.hoa.shopbanhang.domain.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class SignUpEventListener implements ApplicationListener<SignUpEvent> {

  private final ITokenService tokenService;

  public SignUpEventListener(ITokenService tokenService) {
    this.tokenService = tokenService;
  }

  @Override
  public void onApplicationEvent(SignUpEvent event) {
    // Create the Verification Token for the User with link
    User user = event.getUser();
    String token = UUID.randomUUID().toString();
    tokenService.createTokenVerify(token, user);

    // Send email to user
    String url = event.getApplicationUrl()
        + "/api/v1/tokens/verify/"
        + token;

    String contentVerify =
        "Welcome to Shop Cua Hoa."
            + "\nTo activate your account, please click on the following link: " + url
            + "\n\nThank you for using our service.";

    try {
      SendMailUtil.sendMailSimple(user.getEmail(), contentVerify, EmailConstant.SUBJECT_VERIFY);
    } catch (Exception e) {
      throw new NotFoundException(EmailConstant.SEND_FAILED);
    }
  }
}
