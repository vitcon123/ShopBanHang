package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.domain.entities.Cart;

import java.util.List;

public interface ICartService {
  List<Cart> getAll();

  Cart getCartById(Long id);
  Cart getCartByIdUser(Long idUser);

  Cart createCart(Long idUser);

  RequestResponse deleteById(Long id);
}
