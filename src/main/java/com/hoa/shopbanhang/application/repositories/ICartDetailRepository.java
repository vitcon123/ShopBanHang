package com.hoa.shopbanhang.application.repositories;

import com.hoa.shopbanhang.domain.entities.Cart;
import com.hoa.shopbanhang.domain.entities.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ICartDetailRepository extends JpaRepository<CartDetail, Long> {
  @Query("select c from CartDetail c where c.cart = ?1")
  List<CartDetail> getAllByCart(Cart cart);

  @Transactional
  @Modifying
  @Query("delete from CartDetail c where c.cart = ?1")
  void deleteAllByCart(Cart cart);

}
