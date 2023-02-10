package com.vitweb.vitwebapi.application.listens;

import com.vitweb.vitwebapi.application.events.SignUpEvent;
import com.vitweb.vitwebapi.application.services.ITokenService;
import com.vitweb.vitwebapi.application.utils.SendMailUtil;
import com.vitweb.vitwebapi.domain.entities.User;
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
    SendMailUtil.sendMailSimple(user.getEmail(), url, "Verify sign up Vit Web");
  }
}
