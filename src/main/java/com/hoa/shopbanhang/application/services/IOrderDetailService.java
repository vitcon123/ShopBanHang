package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.inputs.order.CreateOrderDetailInput;
import com.hoa.shopbanhang.application.inputs.order.UpdateOrderDetailInput;
import com.hoa.shopbanhang.domain.entities.OrderDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IOrderDetailService {
  List<OrderDetail> getAll();

  OrderDetail getOrderDetailById(Long id);

  OrderDetail createOrderDetail(CreateOrderDetailInput createOrderDetailInput);

  OrderDetail updateOrderDetail(UpdateOrderDetailInput updateOrderDetailInput);

  RequestResponse deleteById(Long id);
}
