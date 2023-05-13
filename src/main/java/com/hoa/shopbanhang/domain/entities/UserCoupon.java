package com.hoa.shopbanhang.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hoa.shopbanhang.application.constants.TableNameConstant;
import com.hoa.shopbanhang.domain.entities.base.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = TableNameConstant.TBL_USER_COUPON)
public class UserCoupon extends AbstractAuditingEntity {

  private String timeExpired;

  private Boolean isUsed = Boolean.FALSE;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private User user;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinColumn(name = "coupon_id")
  private Coupon coupon;

  @OneToOne
  @JoinColumn(name = "order_id")
  @JsonIgnore
  private Order order;

}
