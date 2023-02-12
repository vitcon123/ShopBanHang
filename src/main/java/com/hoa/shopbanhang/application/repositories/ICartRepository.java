package com.hoa.shopbanhang.application.repositories;

import com.hoa.shopbanhang.domain.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartRepository extends JpaRepository<Cart, Long> {
}
