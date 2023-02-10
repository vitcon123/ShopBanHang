package com.vitweb.vitwebapi.application.services.impl;

import com.vitweb.vitwebapi.adapter.web.v1.transfer.response.RequestResponse;
import com.vitweb.vitwebapi.application.constants.CommonConstant;
import com.vitweb.vitwebapi.application.constants.UserMessageConstant;
import com.vitweb.vitwebapi.application.inputs.user.ChangeAvatarInput;
import com.vitweb.vitwebapi.application.inputs.user.UpdateUserInput;
import com.vitweb.vitwebapi.application.repositories.IUserRepository;
import com.vitweb.vitwebapi.application.services.IUserService;
import com.vitweb.vitwebapi.application.utils.CloudinaryUtil;
import com.vitweb.vitwebapi.application.utils.SecurityUtil;
import com.vitweb.vitwebapi.configs.exceptions.VsException;
import com.vitweb.vitwebapi.domain.entities.User;
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
  public List<User> getFollowing(Long id) {
    Optional<User> oldUser = userRepository.findById(id);
    checkUserExists(oldUser);

    return userRepository.getFollowingById(id);
  }

  @Override
  public List<User> getFollowers(Long id) {
    Optional<User> oldUser = userRepository.findById(id);
    checkUserExists(oldUser);

    return userRepository.getFollowersById(id);
  }


  @Override
  public User getUserById(Long id) {
    Optional<User> user = userRepository.findById(id);
    checkUserExists(user);
    return user.get();
  }

  @Override
  public User updateUser(UpdateUserInput updateUserInput) {
    Optional<User> user = userRepository.findById(updateUserInput.getId());
    checkUserExists(user);
    modelMapper.map(updateUserInput, user.get());
    return userRepository.save(user.get());
  }

  @Override
  public User getUserByIdName(String idName) {
    Optional<User> oldUser = userRepository.findByIdName(idName);
    checkUserExists(oldUser);
    return oldUser.get();
  }

  @Override
  public RequestResponse changeAvatar(ChangeAvatarInput changeAvatarInput) {
    Optional<User> oldUser = userRepository.findById(changeAvatarInput.getId());
    checkUserExists(oldUser);
    setAvatarUser(oldUser.get(), changeAvatarInput.getFile());
    userRepository.save(oldUser.get());
    return new RequestResponse(CommonConstant.TRUE, "Change avatar successfully !");
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<User> user = userRepository.findById(id);
    checkUserExists(user);
    userRepository.deleteById(id);
    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  @Override
  public RequestResponse follow(Long idFollow) {
    Optional<User> oldUser = userRepository.findByEmail(SecurityUtil.getCurrentUserLogin());
    checkUserExists(oldUser);

    Optional<User> userFollow = userRepository.findById(idFollow);
    checkUserExists(userFollow);

    List<User> follows = oldUser.get().getFollowings();
    follows.add(userFollow.get());

    oldUser.get().setFollowings(follows);
    userRepository.save(oldUser.get());
    return new RequestResponse(CommonConstant.TRUE, "Follow successfully !");
  }

  @Override
  public RequestResponse unfollow(Long idFollow) {
    Optional<User> oldUser = userRepository.findByEmail(SecurityUtil.getCurrentUserLogin());
    checkUserExists(oldUser);

    Optional<User> userFollow = userRepository.findById(idFollow);
    checkUserExists(userFollow);

    List<User> follows = oldUser.get().getFollowings();
    follows.remove(userFollow.get());

    oldUser.get().setFollowings(follows);
    userRepository.save(oldUser.get());
    return new RequestResponse(CommonConstant.TRUE, "Unfollow successfully !");
  }

  private void checkUserExists(Optional<User> user) {
    if(user.isEmpty()) {
      throw new VsException(UserMessageConstant.User.ERR_NOT_FOUND_BY_ID);
    }
  }

  public void setAvatarUser(User user, MultipartFile file) {
    if(user.getAvatar() != null) {
      CloudinaryUtil.removeFileFromUrl(user.getAvatar());
    }
    user.setAvatar(CloudinaryUtil.getUrlFromFile(file));
  }
}
