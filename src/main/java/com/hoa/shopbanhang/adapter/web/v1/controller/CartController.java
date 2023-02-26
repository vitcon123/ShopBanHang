package com.hoa.shopbanhang.adapter.web.v1.controller;

import com.hoa.shopbanhang.adapter.web.base.RestApiV1;
import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;
import com.hoa.shopbanhang.application.constants.UrlConstant;
import com.hoa.shopbanhang.application.services.ICartDetailService;
import com.hoa.shopbanhang.application.services.ICartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestApiV1
public class CartController {

  private final ICartService cartService;
  private final ICartDetailService cartDetailService;

  public CartController(ICartService cartService, ICartDetailService cartDetailService) {
    this.cartService = cartService;
    this.cartDetailService = cartDetailService;
  }

  @GetMapping(UrlConstant.Cart.LIST)
  public ResponseEntity<?> getAll() {
    return VsResponseUtil.ok(cartService.getAll());
  }

  @GetMapping(UrlConstant.Cart.GET)
  public ResponseEntity<?> getCartById(@PathVariable("idCart") Long idCart) {
    return VsResponseUtil.ok(cartService.getCartById(idCart));
  }

  @PostMapping(UrlConstant.Cart.CREATE)
  public ResponseEntity<?> createCart(@PathVariable("idUser") Long idUser) {
    return VsResponseUtil.ok(cartService.createCart(idUser));
  }

  @DeleteMapping(UrlConstant.Cart.DELETE)
  public ResponseEntity<?> deleteCart(@PathVariable("idCart") Long idCart) {
    return VsResponseUtil.ok(cartService.deleteById(idCart));
  }

  @GetMapping(UrlConstant.Cart.USER_CART)
  public ResponseEntity<?> getCartByUser(@PathVariable(name = "idUser") Long idUser) {
    return VsResponseUtil.ok(cartService.getCartByIdUser(idUser));
  }

  @PostMapping(UrlConstant.Cart.ADD_PRODUCT_TO_CART)
  public ResponseEntity<?> addProductToCartById(@PathVariable(name = "idCart") Long idCart,
                                         @PathVariable(name = "idProduct") Long idProduct,
                                         @RequestParam(name = "amount") Long amount) {
    cartDetailService.addProductToCartById(idCart, idProduct, amount);
    return VsResponseUtil.ok("Add Successfully");
  }

  @PatchMapping(UrlConstant.Cart.EDIT_AMOUNT_OF_CART_DETAIL)
  public ResponseEntity<?> editCartDetailById(@PathVariable(name = "idCartDetail") Long idCartDetail,
                                                  @RequestParam(name = "amount") Long amount) {
    cartDetailService.editCartDetailById(idCartDetail, amount);
    return VsResponseUtil.ok("Edit Amount of CartDetail " + idCartDetail + "successfully");
  }


  @DeleteMapping(UrlConstant.Cart.DELETE_CART_DETAIL)
  public ResponseEntity<?> deleteCartDetailById(@PathVariable(name = "idCartDetail") Long idCartDetail){
    cartDetailService.deleteCartDetailById(idCartDetail);
    return VsResponseUtil.ok("Delete cart detail " + idCartDetail + " successfully");
  }

  @DeleteMapping(UrlConstant.Cart.DELETE_ALL_CART_DETAIL)
  public ResponseEntity<?> deleteAllCartDetailInCart(@PathVariable("idCart") Long idCart) {
    cartDetailService.deleteAllCartDetailInCart(idCart);
    return VsResponseUtil.ok("Delete all cart detail of cart " + idCart + " successfully");
  }

}
