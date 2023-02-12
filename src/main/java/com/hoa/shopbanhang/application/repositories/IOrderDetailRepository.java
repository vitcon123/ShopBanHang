package com.hoa.shopbanhang.application.repositories;

import com.hoa.shopbanhang.domain.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
