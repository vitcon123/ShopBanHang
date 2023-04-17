package com.hoa.shopbanhang.application.inputs.user;

import com.hoa.shopbanhang.application.constants.MessageConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeAvatarInput {

  private Long id;

  private MultipartFile avatar;
}
