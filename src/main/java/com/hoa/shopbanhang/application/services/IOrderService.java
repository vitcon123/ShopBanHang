package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.inputs.order.CreateOrderInput;
import com.hoa.shopbanhang.application.inputs.order.FilterOrderInput;
import com.hoa.shopbanhang.application.inputs.order.UpdateOrderInput;
import com.hoa.shopbanhang.application.outputs.order.GetListOrderOutput;
import com.hoa.shopbanhang.domain.entities.Order;

import java.util.List;

public interface IOrderService {
  List<Order> getAll();

  GetListOrderOutput filterOrders(FilterOrderInput filterOrderInput, Integer page, Integer size);

  Order getOrderById(Long idOrder);

  List<Order> getOrderByUser(Long idUser);

  RequestResponse createOrder(CreateOrderInput createOrderInput);

  RequestResponse updateOrder(UpdateOrderInput updateOrderInput);

  RequestResponse cancelOrder(Long idOrder);

}
