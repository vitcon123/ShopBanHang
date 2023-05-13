package com.hoa.shopbanhang.application.repositories;

import com.hoa.shopbanhang.domain.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICouponRepository extends JpaRepository<Coupon, Long> {


}
