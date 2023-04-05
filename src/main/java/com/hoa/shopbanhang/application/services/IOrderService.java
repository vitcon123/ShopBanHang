package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.OrderDetailOutput;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.inputs.order.CreateOrderInput;
import com.hoa.shopbanhang.domain.entities.Order;

import java.util.List;

public interface IOrderService {
  List<Order> getAll();

  OrderDetailOutput getOrderById(Long idOrder);

  List<Order> getOrderByUser(Long idUser);

  OrderDetailOutput createOrder(CreateOrderInput createOrderInput);
  RequestResponse cancelOrder(Long idOrder);

  RequestResponse setOrderOrderPlaced(Long idOrder);
  RequestResponse setOrderPreparingToShip(Long idOrder);
  RequestResponse setOrderInTransit(Long idOrder);
  RequestResponse setOrderDelivered(Long idOrder);
}
