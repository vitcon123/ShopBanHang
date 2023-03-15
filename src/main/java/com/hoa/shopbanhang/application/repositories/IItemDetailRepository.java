package com.hoa.shopbanhang.application.repositories;

import com.hoa.shopbanhang.domain.entities.Cart;
import com.hoa.shopbanhang.domain.entities.ItemDetail;
import com.hoa.shopbanhang.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface IItemDetailRepository extends JpaRepository<ItemDetail, Long> {
  @Query("select c from ItemDetail c where c.cart = ?1")
  List<ItemDetail> getAllByCart(Cart cart);

  @Query("select i from ItemDetail i where i.order = ?1")
  List<ItemDetail> getAllByOrder(Order order);

  @Transactional
  @Modifying
  @Query("delete from ItemDetail c where c.cart = ?1")
  void deleteAllByCart(Cart cart);

}
