package com.hoa.shopbanhang.domain.entities;

import com.hoa.shopbanhang.application.constants.TableNameConstant;
import com.shop.shopbanhang.domain.entities.base.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TableNameConstant.TBL_MEDIA)
public class Media extends AbstractAuditingEntity {

  private String path;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinColumn(name = "product_id")
  private Product product;

}
