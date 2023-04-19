package com.hoa.shopbanhang.application.repositories;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.AdminStatisticOutput;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.ReportProductOutput;
import com.hoa.shopbanhang.application.inputs.product.ReportProductInput;
import com.hoa.shopbanhang.application.inputs.statistic.AdminStatisticInput;
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


  @Query("select i.product as product, sum(i.amount) as sold " +
      "from ItemDetail i " +
      "where i.order is not null " +
      "and (:#{#reportProductInput.timeStart} is null or i.order.orderedDate >= :#{#reportProductInput.timeStart}) " +
      "and (:#{#reportProductInput.timeEnd} is null or i.order.orderedDate <= :#{#reportProductInput.timeEnd}) " +
      "group by i.product.id " +
      "order by sold DESC")
  List<ReportProductOutput> reportProduct(ReportProductInput reportProductInput);

}
