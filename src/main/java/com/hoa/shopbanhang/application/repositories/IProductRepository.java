package com.hoa.shopbanhang.application.repositories;

import com.hoa.shopbanhang.application.inputs.product.SearchProductInput;
import com.hoa.shopbanhang.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product, Long> {
  @Query("select p from Product p " +
      "where (:#{#searchProductInput.idCategory} is null  or p.category.id = :#{#searchProductInput.idCategory}) " +
      "and (:#{#searchProductInput.name} is null  or p.name like %:#{#searchProductInput.name}%) " +
      "and (:#{#searchProductInput.price} is null  or p.price between (:#{#searchProductInput.price} - 50.0) and (:#{#searchProductInput.price} + 50.0)) " +
      "and (:#{#searchProductInput.stock} is null  or p.stock between (:#{#searchProductInput.stock} - 1) and (:#{#searchProductInput.stock} + 1))" +
      "and (:#{#searchProductInput.brand} is null  or p.brand like %:#{#searchProductInput.brand}%)")
  List<Product> searchProducts(SearchProductInput searchProductInput);
}
