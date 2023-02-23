package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.inputs.cart_detail.CreateCartDetailInput;
import com.hoa.shopbanhang.application.inputs.cart_detail.UpdateCartDetailInput;
import com.hoa.shopbanhang.domain.entities.CartDetail;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ICartDetailService {
  List<CartDetail> getAll();

  CartDetail getCartDetailById(Long id);

  CartDetail createCartDetail(CreateCartDetailInput createCartDetailInput);

  CartDetail updateCartDetail(UpdateCartDetailInput updateCartDetailInput);

  RequestResponse deleteById(Long id);
}
