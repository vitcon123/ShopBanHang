package com.hoa.shopbanhang.application.repositories;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.AdminStatisticOutput;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.CheckSpamStatisticOutput;
import com.hoa.shopbanhang.application.inputs.statistic.AdminStatisticInput;
import com.hoa.shopbanhang.domain.entities.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IStatisticRepository extends JpaRepository<Statistic, Long> {

  @Query("select s.product as product, count(s) as times " +
      "from Statistic s " +
      "where (:#{#adminStatisticInput.ageMin} is null or s.ageOfUser >= :#{#adminStatisticInput.ageMin}) " +
      "and (:#{#adminStatisticInput.ageMax} is null or s.ageOfUser <= :#{#adminStatisticInput.ageMax}) " +
      "and (:#{#adminStatisticInput.timeStart} is null or s.timeView >= :#{#adminStatisticInput.timeStart}) " +
      "and (:#{#adminStatisticInput.timeEnd} is null or s.timeView <= :#{#adminStatisticInput.timeEnd})" +
      "group by s.product.id " +
      "order by times desc ")
  List<AdminStatisticOutput> adminStatistic(AdminStatisticInput adminStatisticInput);

  @Query("select s.user as user, s.product as product, count(s) as times " +
      "from Statistic s " +
      "where (:userId is null or s.user.id = :userId) " +
      "and (:productId is null or s.product.id = :productId) " +
      "group by s.user.id, s.product.id")
  CheckSpamStatisticOutput checkSpamStatisticOutput(Long userId, Long productId);

}
