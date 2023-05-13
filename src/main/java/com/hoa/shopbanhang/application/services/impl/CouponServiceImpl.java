package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.constants.MessageConstant;
import com.hoa.shopbanhang.application.inputs.coupon.CreateCouponInput;
import com.hoa.shopbanhang.application.inputs.coupon.UpdateCouponInput;
import com.hoa.shopbanhang.application.repositories.ICouponRepository;
import com.hoa.shopbanhang.application.repositories.IUserCouponRepository;
import com.hoa.shopbanhang.application.repositories.IUserRepository;
import com.hoa.shopbanhang.application.services.ICouponService;
import com.hoa.shopbanhang.configs.exceptions.VsException;
import com.hoa.shopbanhang.domain.entities.Coupon;
import com.hoa.shopbanhang.domain.entities.User;
import com.hoa.shopbanhang.domain.entities.UserCoupon;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CouponServiceImpl implements ICouponService {
  private final ICouponRepository couponRepository;
  private final IUserRepository userRepository;
  private final IUserCouponRepository userCouponRepository;
  private final ModelMapper modelMapper;

  public CouponServiceImpl(ICouponRepository couponRepository, IUserRepository userRepository, IUserCouponRepository userCouponRepository, ModelMapper modelMapper) {
    this.couponRepository = couponRepository;
    this.userRepository = userRepository;
    this.userCouponRepository = userCouponRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<Coupon> getAll() {
    return couponRepository.findAll();
  }

  @Override
  public List<UserCoupon> getAllCouponOfUser(Long userId) {
      Optional<User> user = userRepository.findById(userId);
      UserServiceImpl.checkUserExists(user);
      List<UserCoupon> userCoupons = userCouponRepository.getAllUserCouponByUser(user.get());
//      List<UserCoupon> userCoupons = user.get().getUserCoupons();
      List<Coupon> coupons = new ArrayList<>();
      for (UserCoupon userCoupon : userCoupons) {
        coupons.add(userCoupon.getCoupon());
      }
      return userCoupons;
  }

  @Override
  public Coupon getCouponById(Long id) {
    Optional<Coupon> oldCoupon = couponRepository.findById(id);
    checkCouponExists(oldCoupon);

    return oldCoupon.get();
  }

  @Override
  public Coupon createCoupon(CreateCouponInput createCouponInput) {

    Coupon coupon = modelMapper.map(createCouponInput, Coupon.class);

    return couponRepository.save(coupon);
  }

  @Override
  public Coupon updateCoupon(UpdateCouponInput updateCouponInput) {
    Optional<Coupon> oldCoupon = couponRepository.findById(updateCouponInput.getId());
    checkCouponExists(oldCoupon);

    modelMapper.map(updateCouponInput, oldCoupon.get());

    return couponRepository.save(oldCoupon.get());
  }

  @Override
  public RequestResponse deleteById(Long id) {
    Optional<Coupon> oldCoupon = couponRepository.findById(id);
    checkCouponExists(oldCoupon);

    couponRepository.delete(oldCoupon.get());

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  public static void checkCouponExists(Optional<Coupon> Coupon) {
    if(Coupon.isEmpty()) {
      throw new VsException(MessageConstant.COUPON_NOT_EXISTS);
    }
  }

}
