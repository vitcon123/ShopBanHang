package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.constants.DevMessageConstant;
import com.hoa.shopbanhang.application.constants.UserMessageConstant;
import com.hoa.shopbanhang.application.inputs.user.ChangeAvatarInput;
import com.hoa.shopbanhang.application.inputs.user.UpdateUserInput;
import com.hoa.shopbanhang.application.repositories.IUserRepository;
import com.hoa.shopbanhang.application.services.IUserService;
import com.hoa.shopbanhang.application.utils.CloudinaryUtil;
import com.hoa.shopbanhang.configs.exceptions.VsException;
import com.hoa.shopbanhang.domain.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

  private final IUserRepository userRepository;
  private final ModelMapper modelMapper;

  public UserServiceImpl(IUserRepository userRepository, ModelMapper modelMapper) {
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<User> getAll() {
    return userRepository.findAll();
  }

  @Override
  public User getUserById(Long id) {
    Optional<User> user = userRepository.findById(id);
    UserServiceImpl.checkUserExists(user, id);
    return user.get();
  }

  @Override
  public User updateUser(UpdateUserInput updateUserInput) {
    Optional<User> user = userRepository.findById(updateUserInput.getId());
    UserServiceImpl.checkUserExists(user, updateUserInput.getId());
    modelMapper.map(updateUserInput, user.get());
    return userRepository.save(user.get());
  }

  @Override
  public RequestResponse changeAvatar(ChangeAvatarInput changeAvatarInput) {
    Optional<User> oldUser = userRepository.findById(changeAvatarInput.getId());
    UserServiceImpl.checkUserExists(oldUser, changeAvatarInput.getId());
    setAvatarUser(oldUser.get(), changeAvatarInput.getFile());
    userRepository.save(oldUser.get());
    return new RequestResponse(CommonConstant.TRUE, "Change avatar successfully !");
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<User> user = userRepository.findById(id);
    UserServiceImpl.checkUserExists(user, id);
    userRepository.deleteById(id);
    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  public static void checkUserExists(Optional<User> user, Long id) {
    if(user.isEmpty()) {
      throw new VsException(UserMessageConstant.User.ERR_NOT_FOUND_BY_ID,
          String.format(DevMessageConstant.User.ERR_NOT_FOUND_BY_ID, id),
          new String[]{id.toString()});
    }
  }

  public void setAvatarUser(User user, MultipartFile file) {
    if(user.getAvatar() != null) {
      CloudinaryUtil.removeFileFromUrl(user.getAvatar());
    }
    user.setAvatar(CloudinaryUtil.getUrlFromFile(file));
  }
}
