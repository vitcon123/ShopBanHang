package com.hoa.shopbanhang.application.inputs.notification;

import com.hoa.shopbanhang.application.constants.UserMessageConstant;
import com.hoa.shopbanhang.application.utils.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNotificationInput {

  @NotNull(message = UserMessageConstant.INVALID_SOME_THING_FIELD_IS_REQUIRED)
  private Long id;

  private String content;

  private String path;

  private Boolean isRead;

  private String lastModifiedBy = SecurityUtil.getCurrentUserLogin();

}
