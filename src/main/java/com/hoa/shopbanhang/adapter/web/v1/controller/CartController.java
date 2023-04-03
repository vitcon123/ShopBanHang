package com.hoa.shopbanhang.adapter.web.v1.controller;

import com.hoa.shopbanhang.adapter.web.base.RestApiV1;
import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;
import com.hoa.shopbanhang.application.constants.UrlConstant;
import com.hoa.shopbanhang.application.services.IItemDetailService;
import com.hoa.shopbanhang.application.services.ICartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestApiV1
public class CartController {

  private final ICartService cartService;
  private final IItemDetailService itemDetailService;

  public CartController(ICartService cartService, IItemDetailService itemDetailService) {
    this.cartService = cartService;
    this.itemDetailService = itemDetailService;
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
                                         @RequestParam(name = "amount") Integer amount) {
    itemDetailService.addProductToCartById(idCart, idProduct, amount);
    return VsResponseUtil.ok("Add Successfully");
  }

  @PatchMapping(UrlConstant.Cart.EDIT_AMOUNT_OF_CART_DETAIL)
  public ResponseEntity<?> editItemDetailById(@PathVariable(name = "idItemDetail") Long idItemDetail,
                                                  @RequestParam(name = "amount") Integer amount) {
    itemDetailService.editItemDetailById(idItemDetail, amount);
    return VsResponseUtil.ok("Edit Amount of ItemDetail " + idItemDetail + "successfully");
  }


  @DeleteMapping(UrlConstant.Cart.DELETE_CART_DETAIL)
  public ResponseEntity<?> deleteItemDetailById(@PathVariable(name = "idItemDetail") Long idItemDetail){
    itemDetailService.deleteItemDetailById(idItemDetail);
    return VsResponseUtil.ok("Delete cart detail " + idItemDetail + " successfully");
  }

  @DeleteMapping(UrlConstant.Cart.DELETE_ALL_CART_DETAIL)
  public ResponseEntity<?> deleteAllItemDetailInCart(@PathVariable("idCart") Long idCart) {
    itemDetailService.deleteAllItemDetailInCart(idCart);
    return VsResponseUtil.ok("Delete all cart detail of cart " + idCart + " successfully");
  }

}
