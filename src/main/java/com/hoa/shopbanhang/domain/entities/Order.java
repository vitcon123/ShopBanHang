package com.hoa.shopbanhang.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hoa.shopbanhang.application.constants.DeliveryMethod;
import com.hoa.shopbanhang.application.constants.DeliveryStatus;
import com.hoa.shopbanhang.application.constants.PaymentMethod;
import com.hoa.shopbanhang.application.constants.TableNameConstant;
import com.hoa.shopbanhang.domain.entities.base.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = TableNameConstant.TBL_ORDER)
public class Order extends AbstractAuditingEntity {

  private String fullName;

  private String address;

  private String phone;

  @Enumerated(EnumType.STRING)
  private DeliveryStatus deliveryStatus;

  private String orderedDate = LocalDate.now().toString();

  private String deliveredDate;

  @Enumerated(EnumType.STRING)
  private DeliveryMethod deliveryMethod;

  @Enumerated(EnumType.STRING)
  private PaymentMethod paymentMethod;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
//  @JsonIgnore
  private List<ItemDetail> itemDetails;

  @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private UserCoupon userCoupon;

}
