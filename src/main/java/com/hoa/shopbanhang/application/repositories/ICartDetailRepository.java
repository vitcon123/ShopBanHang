package com.hoa.shopbanhang.application.repositories;

import com.hoa.shopbanhang.domain.entities.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartDetailRepository extends JpaRepository<CartDetail, Long> {
}
