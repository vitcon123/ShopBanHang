package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.inputs.cart_detail.CreateCartDetailInput;
import com.hoa.shopbanhang.application.inputs.cart_detail.UpdateCartDetailInput;
import com.hoa.shopbanhang.domain.entities.CartDetail;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ICartDetailService {
  List<CartDetail> getAllCartDetailInCart(Long idCart);
  void addProductToCartById(Long idCart, Long idProduct, Long amount);
  void editCartDetailById(Long idCartDetail, Long amount);
  void deleteCartDetailById(Long idCartDetail);
  void deleteAllCartDetailInCart(Long idCart);

}
