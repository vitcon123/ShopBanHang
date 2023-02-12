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
  public ResponseEntity<?> getOrderById(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(orderService.getOrderById(id));
  }

  @PostMapping(UrlConstant.Order.CREATE)
  public ResponseEntity<?> createOrder(@ModelAttribute CreateOrderInput createOrderInput) {
    return VsResponseUtil.ok(orderService.createOrder(createOrderInput));
  }

  @PatchMapping(UrlConstant.Order.UPDATE)
  public ResponseEntity<?> updateOrder(@Valid @ModelAttribute UpdateOrderInput updateOrderInput) {
    return VsResponseUtil.ok(orderService.updateOrder(updateOrderInput));
  }

  @DeleteMapping(UrlConstant.Order.DELETE)
  public ResponseEntity<?> deleteOrder(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(orderService.deleteById(id));
  }
}
