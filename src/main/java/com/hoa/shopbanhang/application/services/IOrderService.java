package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.inputs.order.CreateOrderInput;
import com.hoa.shopbanhang.application.inputs.order.UpdateOrderInput;
import com.hoa.shopbanhang.domain.entities.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IOrderService {
  List<Order> getAll();

  Order getOrderById(Long id);

  Order createOrder(CreateOrderInput createOrderInput);

  Order updateOrder(UpdateOrderInput updateOrderInput);

  RequestResponse deleteById(Long id);
}
