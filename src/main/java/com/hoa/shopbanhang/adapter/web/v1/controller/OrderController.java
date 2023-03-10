package com.hoa.shopbanhang.adapter.web.v1.controller;

import com.hoa.shopbanhang.adapter.web.base.RestApiV1;
import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;
import com.hoa.shopbanhang.application.constants.UrlConstant;
import com.hoa.shopbanhang.application.inputs.order.CreateOrderInput;
import com.hoa.shopbanhang.application.inputs.order.UpdateOrderInput;
import com.hoa.shopbanhang.application.services.IOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestApiV1
public class OrderController {

  private final IOrderService orderService;

  public OrderController(IOrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping(UrlConstant.Order.LIST)
  public ResponseEntity<?> getAll() {
    return VsResponseUtil.ok(orderService.getAll());
  }

  @GetMapping(UrlConstant.Order.GET)
  public ResponseEntity<?> getOrderById(@PathVariable("idOrder") Long idOrder) {
    return VsResponseUtil.ok(orderService.getOrderById(idOrder));
  }

  @PostMapping(UrlConstant.Order.CREATE)
  public ResponseEntity<?> createOrder(@RequestBody CreateOrderInput createOrderInput) {
    return VsResponseUtil.ok(orderService.createOrder(createOrderInput));
  }

  @PatchMapping(UrlConstant.Order.ORDER_PLACED)
  public ResponseEntity<?> setOrderOrderPlaced(@PathVariable("idOrder") Long idOrder) {
    return VsResponseUtil.ok(orderService.setOrderOrderPlaced(idOrder));
  }

  @PatchMapping(UrlConstant.Order.PREPARING_TO_SHIP)
  public ResponseEntity<?> setOrderPreparingToShip(@PathVariable("idOrder") Long idOrder) {
    return VsResponseUtil.ok(orderService.setOrderPreparingToShip(idOrder));
  }

  @PatchMapping(UrlConstant.Order.IN_TRANSIT)
  public ResponseEntity<?> setOrderInTransit(@PathVariable("idOrder") Long idOrder) {
    return VsResponseUtil.ok(orderService.setOrderInTransit(idOrder));
  }

  @PatchMapping(UrlConstant.Order.DELIVERED)
  public ResponseEntity<?> setOrderDelivered(@PathVariable("idOrder") Long idOrder) {
    return VsResponseUtil.ok(orderService.setOrderDelivered(idOrder));
  }

  @DeleteMapping(UrlConstant.Order.DELETE)
  public ResponseEntity<?> deleteOrder(@PathVariable("idOrder") Long idOrder) {
    return VsResponseUtil.ok(orderService.deleteById(idOrder));
  }
}
