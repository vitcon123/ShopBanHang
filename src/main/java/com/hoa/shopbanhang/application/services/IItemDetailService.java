package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.ReportProductOutput;
import com.hoa.shopbanhang.application.inputs.product.ReportProductInput;
import com.hoa.shopbanhang.domain.entities.ItemDetail;

import java.util.List;

public interface IItemDetailService {
  List<ItemDetail> getAllItemDetailInCart(Long idCart);
  void addProductToCartById(Long idUser, Long idProduct, Integer amount);
  void editItemDetailById(Long idItemDetail, Integer amount);
  void deleteItemDetailById(Long idItemDetail);
  void deleteAllItemDetailInCart(Long idCart);

  List<ReportProductOutput> reportProduct(ReportProductInput reportProductInput);

}
