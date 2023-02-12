package com.hoa.shopbanhang.application.repositories;

import com.hoa.shopbanhang.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order, Long> {
}
