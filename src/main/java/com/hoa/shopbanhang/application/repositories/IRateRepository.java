package com.hoa.shopbanhang.application.repositories;

import com.hoa.shopbanhang.domain.entities.Product;
import com.hoa.shopbanhang.domain.entities.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRateRepository extends JpaRepository<Rate, Long> {

  @Query("SELECT AVG(r.star) FROM Rate r WHERE r.product = ?1")
  Double getAvgOfProduct(Product product);

}
