package com.hoa.shopbanhang.application.repositories;

import com.hoa.shopbanhang.domain.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ICartRepository extends JpaRepository<Cart, Long> {

  @Query("select c from Cart c where c.user.id = ?1")
  Cart findCartByUser(Long userId);

}
