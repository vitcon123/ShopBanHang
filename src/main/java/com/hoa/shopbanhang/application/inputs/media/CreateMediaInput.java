package com.hoa.shopbanhang.application.inputs.media;

import com.hoa.shopbanhang.application.constants.UserMessageConstant;
import com.hoa.shopbanhang.application.utils.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMediaInput {

  private Long idProduct;

  @NotNull(message = UserMessageConstant.INVALID_SOME_THING_FIELD_IS_REQUIRED)
  private MultipartFile file;

  private String createdBy = SecurityUtil.getCurrentUserLogin();

  private String lastModifiedBy = SecurityUtil.getCurrentUserLogin();
}
