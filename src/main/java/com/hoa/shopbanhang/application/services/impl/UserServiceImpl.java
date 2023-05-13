package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.constants.MessageConstant;
import com.hoa.shopbanhang.application.inputs.user.ChangeAvatarInput;
import com.hoa.shopbanhang.application.inputs.user.UpdateUserInput;
import com.hoa.shopbanhang.application.repositories.ICouponRepository;
import com.hoa.shopbanhang.application.repositories.IUserRepository;
import com.hoa.shopbanhang.application.services.IUserService;
import com.hoa.shopbanhang.application.utils.CloudinaryUtil;
import com.hoa.shopbanhang.configs.exceptions.VsException;
import com.hoa.shopbanhang.domain.entities.Coupon;
import com.hoa.shopbanhang.domain.entities.User;
import com.hoa.shopbanhang.domain.entities.UserCoupon;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

  private final IUserRepository userRepository;
  private final ICouponRepository couponRepository;
  private final ModelMapper modelMapper;

  public UserServiceImpl(IUserRepository userRepository, ICouponRepository couponRepository, ModelMapper modelMapper) {
    this.userRepository = userRepository;
    this.couponRepository = couponRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<User> getAll() {
    return userRepository.findAll();
  }

  @Override
  public List<User> getAllUserOfCoupon(Long couponId) {
    Optional<Coupon> coupon = couponRepository.findById(couponId);
    CouponServiceImpl.checkCouponExists(coupon);
    List<UserCoupon> userCoupons = coupon.get().getUserCoupons();
    List<User> users = new ArrayList<>();
    for (UserCoupon userCoupon : userCoupons) {
      users.add(userCoupon.getUser());
    }
    return users;
  }

  @Override
  public User getUserById(Long id) {
    Optional<User> user = userRepository.findById(id);
    UserServiceImpl.checkUserExists(user);
    return user.get();
  }

  @Override
  public User updateUser(UpdateUserInput updateUserInput) {
    Optional<User> user = userRepository.findById(updateUserInput.getId());
    UserServiceImpl.checkUserExists(user);
    modelMapper.map(updateUserInput, user.get());
    return userRepository.save(user.get());
  }

  @Override
  public RequestResponse changeAvatar(ChangeAvatarInput changeAvatarInput) {
    Optional<User> oldUser = userRepository.findById(changeAvatarInput.getId());
    UserServiceImpl.checkUserExists(oldUser);
    setAvatarUser(oldUser.get(), changeAvatarInput.getAvatar());
    userRepository.save(oldUser.get());
    return new RequestResponse(CommonConstant.TRUE, "Change avatar successfully !");
  }

  @Override
  public RequestResponse deleteById(Long id) {
    if(id == 1) {
      throw new VsException(MessageConstant.CANNOT_DELETE_ADMIN_ACCOUNT);
    }
    Optional<User> user = userRepository.findById(id);
    UserServiceImpl.checkUserExists(user);
    userRepository.deleteById(id);
    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  public static void checkUserExists(Optional<User> user) {
    if(user.isEmpty()) {
      throw new VsException(MessageConstant.USER_NOT_EXISTS);
    }
  }

  public void setAvatarUser(User user, MultipartFile file) {
    if(user.getAvatar() != null) {
      CloudinaryUtil.removeFileFromUrl(user.getAvatar());
    }
    user.setAvatar(CloudinaryUtil.getUrlFromFile(file));
  }
}
