package com.hoa.shopbanhang.application.repositories;

import com.hoa.shopbanhang.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Long> {
}
