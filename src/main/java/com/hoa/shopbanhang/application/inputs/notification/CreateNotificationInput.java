package com.hoa.shopbanhang.application.inputs.notification;

import com.hoa.shopbanhang.application.constants.UserMessageConstant;
import com.hoa.shopbanhang.application.utils.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotificationInput {

  @NotBlank(message = UserMessageConstant.INVALID_SOME_THING_FIELD_IS_REQUIRED)
  private String content;

  @NotBlank(message = UserMessageConstant.INVALID_SOME_THING_FIELD_IS_REQUIRED)
  private String path;

  private Boolean isRead = Boolean.FALSE;

  private Long userId;

  private String createdBy = SecurityUtil.getCurrentUserLogin();

  private String lastModifiedBy = SecurityUtil.getCurrentUserLogin();

}
