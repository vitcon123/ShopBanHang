package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.constants.MessageConstant;
import com.hoa.shopbanhang.application.inputs.coupon.AddCouponToUsersInput;
import com.hoa.shopbanhang.application.inputs.coupon.DeleteCouponToUsersInput;
import com.hoa.shopbanhang.application.repositories.ICouponRepository;
import com.hoa.shopbanhang.application.repositories.IUserCouponRepository;
import com.hoa.shopbanhang.application.repositories.IUserRepository;
import com.hoa.shopbanhang.application.services.IUserCouponService;
import com.hoa.shopbanhang.configs.exceptions.VsException;
import com.hoa.shopbanhang.domain.entities.Coupon;
import com.hoa.shopbanhang.domain.entities.User;
import com.hoa.shopbanhang.domain.entities.UserCoupon;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserCouponServiceImpl implements IUserCouponService {
  private final IUserCouponRepository userCouponRepository;
  private final ICouponRepository couponRepository;
  private final IUserRepository userRepository;
  private final ModelMapper modelMapper;

  public UserCouponServiceImpl(IUserCouponRepository userCouponRepository, ICouponRepository couponRepository, IUserRepository userRepository, ModelMapper modelMapper) {
    this.userCouponRepository = userCouponRepository;
    this.couponRepository = couponRepository;
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  @Transactional
  public RequestResponse addCouponToUsers(AddCouponToUsersInput input) {
    Optional<Coupon> coupon = couponRepository.findById(input.getCouponId());
    CouponServiceImpl.checkCouponExists(coupon);
    for (Long userId : input.getUserIds()) {
      Optional<User> user = userRepository.findById(userId);
      UserServiceImpl.checkUserExists(user);

      UserCoupon userCoupon = userCouponRepository.getUserCouponByCouponAndUser(coupon.get(), user.get());
      if(userCoupon != null) {
        throw new VsException(MessageConstant.USER_ALREADY_HAS_THIS_COUPON);
      }

      userCoupon = new UserCoupon();
      userCoupon.setUser(user.get());
      userCoupon.setCoupon(coupon.get());
      userCoupon.setTimeExpired(input.getTimeExpired());

      userCouponRepository.save(userCoupon);
    }
    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  @Override
  @Transactional
  public RequestResponse deleteCouponToUsers(DeleteCouponToUsersInput input) {
    Optional<Coupon> coupon = couponRepository.findById(input.getCouponId());
    CouponServiceImpl.checkCouponExists(coupon);
    for (Long userId : input.getUserIds()) {
      Optional<User> user = userRepository.findById(userId);
      UserServiceImpl.checkUserExists(user);
      UserCoupon userCoupon = null;
      userCoupon = userCouponRepository.getUserCouponByCouponAndUser(coupon.get(), user.get());
      if (userCoupon != null) {
        userCouponRepository.delete(userCoupon);
      }
    }
    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }
}
