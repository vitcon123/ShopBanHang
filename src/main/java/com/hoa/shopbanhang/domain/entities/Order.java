package com.hoa.shopbanhang.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = TableNameConstant.TBL_ORDER)
public class Order extends AbstractAuditingEntity {

  private DeliveryStatus deliveryStatus;

  private Timestamp orderedDate = getCreatedDate();

  private String deliveredDate;

  private PaymentMethod paymentMethod;

  private String address;

  private String phone;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
  @JsonIgnore
  private List<ItemDetail> itemDetails;

}
