package com.hoa.shopbanhang.application.repositories;

import com.hoa.shopbanhang.domain.entities.Coupon;
import com.hoa.shopbanhang.domain.entities.User;
import com.hoa.shopbanhang.domain.entities.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserCouponRepository extends JpaRepository<UserCoupon, Long> {

  @Query("select u from UserCoupon u where u.coupon = ?1 and u.user = ?2")
  UserCoupon getUserCouponByCouponAndUser(Coupon coupon, User user);

  @Query("select u " +
      "from UserCoupon u " +
      "where u.user = ?1 " +
      "and u.coupon.amountUsed <= u.coupon.amountPlan " +
      "and u.isUsed = false " +
      "and CURRENT_DATE <= u.timeExpired")
  List<UserCoupon> getAllUserCouponByUser(User user);

  @Query("select u " +
      "from UserCoupon u " +
      "where u.id = ?1 " +
      "and u.user = ?2 " +
      "and u.coupon.amountUsed <= u.coupon.amountPlan " +
      "and u.isUsed = false " +
      "and CURRENT_DATE <= u.timeExpired")
  UserCoupon getUserCouponById(Long idCoupon, User user);

}
