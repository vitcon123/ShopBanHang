package com.hoa.shopbanhang.adapter.web.v1.controller;

import com.hoa.shopbanhang.adapter.web.base.RestApiV1;
import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;
import com.hoa.shopbanhang.application.constants.UrlConstant;
import com.hoa.shopbanhang.application.inputs.order.CreateOrderDetailInput;
import com.hoa.shopbanhang.application.inputs.order.UpdateOrderDetailInput;
import com.hoa.shopbanhang.application.services.IOrderDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestApiV1
public class OrderDetailController {

  private final IOrderDetailService orderDetailService;

  public OrderDetailController(IOrderDetailService orderDetailService) {
    this.orderDetailService = orderDetailService;
  }

  @GetMapping(UrlConstant.OrderDetail.LIST)
  public ResponseEntity<?> getAll() {
    return VsResponseUtil.ok(orderDetailService.getAll());
  }

  @GetMapping(UrlConstant.OrderDetail.GET)
  public ResponseEntity<?> getOrderDetailById(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(orderDetailService.getOrderDetailById(id));
  }

  @PostMapping(UrlConstant.OrderDetail.CREATE)
  public ResponseEntity<?> createOrderDetail(@ModelAttribute CreateOrderDetailInput createOrderDetailInput) {
    return VsResponseUtil.ok(orderDetailService.createOrderDetail(createOrderDetailInput));
  }

  @PatchMapping(UrlConstant.OrderDetail.UPDATE)
  public ResponseEntity<?> updateOrderDetail(@Valid @ModelAttribute UpdateOrderDetailInput updateOrderDetailInput) {
    return VsResponseUtil.ok(orderDetailService.updateOrderDetail(updateOrderDetailInput));
  }

  @DeleteMapping(UrlConstant.OrderDetail.DELETE)
  public ResponseEntity<?> deleteOrderDetail(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(orderDetailService.deleteById(id));
  }
}
