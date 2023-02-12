package com.hoa.shopbanhang.adapter.web.v1.controller;

import com.hoa.shopbanhang.adapter.web.base.RestApiV1;

import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;

import com.hoa.shopbanhang.application.constants.UrlConstant;
import com.hoa.shopbanhang.application.inputs.cart.CreateCartInput;
import com.hoa.shopbanhang.application.inputs.cart.UpdateCartInput;
import com.hoa.shopbanhang.application.services.ICartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestApiV1
public class CartController {

  private final ICartService cartService;

  public CartController(ICartService cartService) {
    this.cartService = cartService;
  }

  @GetMapping(UrlConstant.Cart.LIST)
  public ResponseEntity<?> getAll() {
    return VsResponseUtil.ok(cartService.getAll());
  }

  @GetMapping(UrlConstant.Cart.GET)
  public ResponseEntity<?> getCartById(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(cartService.getCartById(id));
  }

  @PostMapping(UrlConstant.Cart.CREATE)
  public ResponseEntity<?> createCart(@ModelAttribute CreateCartInput createCartInput) {
    return VsResponseUtil.ok(cartService.createCart(createCartInput));
  }

  @PatchMapping(UrlConstant.Cart.UPDATE)
  public ResponseEntity<?> updateCart(@Valid @ModelAttribute UpdateCartInput updateCartInput) {
    return VsResponseUtil.ok(cartService.updateCart(updateCartInput));
  }

  @DeleteMapping(UrlConstant.Cart.DELETE)
  public ResponseEntity<?> deleteCart(@PathVariable("id") Long id) {
    return VsResponseUtil.ok(cartService.deleteById(id));
  }
}
