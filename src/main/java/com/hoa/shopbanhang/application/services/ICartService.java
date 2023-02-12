package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.inputs.cart.CreateCartInput;
import com.hoa.shopbanhang.application.inputs.cart.UpdateCartInput;
import com.hoa.shopbanhang.domain.entities.Cart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICartService {
  List<Cart> getAll();

  Cart getCartById(Long id);

  Cart createCart(CreateCartInput createCartInput);

  Cart updateCart(UpdateCartInput updateCartInput);

  RequestResponse deleteById(Long id);
}
