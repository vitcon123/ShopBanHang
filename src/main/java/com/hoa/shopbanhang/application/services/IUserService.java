package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.inputs.user.ChangeAvatarInput;
import com.hoa.shopbanhang.application.inputs.user.UpdateUserInput;
import com.hoa.shopbanhang.domain.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IUserService {

  List<User> getAll();

  User getUserById(Long id);

  User updateUser(UpdateUserInput updateUserInput);

  RequestResponse changeAvatar(ChangeAvatarInput changeAvatarInput);

  RequestResponse deleteById(Long id);
}
