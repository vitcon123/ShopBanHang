package com.hoa.shopbanhang.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hoa.shopbanhang.application.constants.TableNameConstant;
import com.hoa.shopbanhang.domain.entities.base.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = TableNameConstant.TBL_COUPON)
public class Coupon extends AbstractAuditingEntity {

  private String code;

  private Long amountPlan;

  private Long amountUsed;

  private Double value;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "coupon")
  @JsonIgnore
  private List<UserCoupon> userCoupons;

}
