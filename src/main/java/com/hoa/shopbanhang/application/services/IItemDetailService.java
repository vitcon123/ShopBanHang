package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.domain.entities.ItemDetail;

import java.util.List;

public interface IItemDetailService {
  List<ItemDetail> getAllItemDetailInCart(Long idCart);
  void addProductToCartById(Long idUser, Long idProduct, Integer amount);
  void editItemDetailById(Long idItemDetail, Integer amount);
  void deleteItemDetailById(Long idItemDetail);
  void deleteAllItemDetailInCart(Long idCart);

}
