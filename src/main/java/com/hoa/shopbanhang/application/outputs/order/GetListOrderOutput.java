package com.hoa.shopbanhang.application.outputs.order;

import com.hoa.shopbanhang.application.outputs.common.PagingMeta;
import com.hoa.shopbanhang.domain.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetListOrderOutput {

  private List<Order> orders;

  private PagingMeta meta;

}
