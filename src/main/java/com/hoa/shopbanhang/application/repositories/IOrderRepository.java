package com.hoa.shopbanhang.application.repositories;

import com.hoa.shopbanhang.application.inputs.order.FilterOrderInput;
import com.hoa.shopbanhang.domain.entities.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IOrderRepository extends JpaRepository<Order, Long> {

   @Query("select o " +
        "from Order o " +
        "where (:#{#filterOrderInput.deliveryStatus} is null  or o.deliveryStatus = :#{#filterOrderInput.deliveryStatus}) " +
        "and (:#{#filterOrderInput.paymentMethod} is null  or o.paymentMethod = :#{#filterOrderInput.paymentMethod})")
  List<Order> findOrders(FilterOrderInput filterOrderInput, PageRequest pageRequest);

}
