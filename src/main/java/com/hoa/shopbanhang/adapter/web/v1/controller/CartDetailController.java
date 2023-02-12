package com.hoa.shopbanhang.adapter.web.v1.controller;

import com.hoa.shopbanhang.adapter.web.base.RestApiV1;
import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;
import com.hoa.shopbanhang.application.constants.UrlConstant;
import com.hoa.shopbanhang.application.inputs.cart_detail.CreateCartDetailInput;
import com.hoa.shopbanhang.application.inputs.cart_detail.UpdateCartDetailInput;
import com.hoa.shopbanhang.application.services.ICartDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestApiV1
public class CartDetailController {

  private final ICartDetailService cartDetailService;

  public CartDetailController(ICartDetailService cartDetailService) {
    this.cartDetailService = cartDetailService;
  }

  @GetMapping(UrlConstant.CartDetail.LIST)
  public ResponseEntity<?> getAll() {
    return VsResponseUtil.ok(cartDetailService.getAll());
  }

  @GetMapping(UrlConstant.CartDetail.GET)
  public ResponseEntity<?> getCartDetailById(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(cartDetailService.getCartDetailById(id));
  }

  @PostMapping(UrlConstant.CartDetail.CREATE)
  public ResponseEntity<?> createCartDetail(@ModelAttribute CreateCartDetailInput createCartDetailInput) {
    return VsResponseUtil.ok(cartDetailService.createCartDetail(createCartDetailInput));
  }

  @PatchMapping(UrlConstant.CartDetail.UPDATE)
  public ResponseEntity<?> updateCartDetail(@Valid @ModelAttribute UpdateCartDetailInput updateCartDetailInput) {
    return VsResponseUtil.ok(cartDetailService.updateCartDetail(updateCartDetailInput));
  }

  @DeleteMapping(UrlConstant.CartDetail.DELETE)
  public ResponseEntity<?> deleteCartDetail(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(cartDetailService.deleteById(id));
  }
}
