package com.hoa.shopbanhang.application.repositories;

import com.hoa.shopbanhang.domain.entities.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product, Long> {

//  @Query("select count(p) from Product p " +
//      "where (:#{#findProductInput.idCategory} is null  or p.category.id = :#{#findProductInput.idCategory}) " +
//      "and (:#{#findProductInput.name} is null  or p.name like %:#{#findProductInput.name}%) " +
//      "and (:#{#findProductInput.price} is null  or p.price between (:#{#findProductInput.price} - 50.0) and " +
//      "(:#{#findProductInput.price} + 50.0)) " +
//      "and (:#{#findProductInput.stock} is null  or p.stock between (:#{#findProductInput.stock} - 5) and " +
//      "(:#{#findProductInput.stock} + 5))" +
//      "and (:#{#findProductInput.brand} is null  or p.brand like %:#{#findProductInput.brand}%)")
//  Long countProduct(FindProductInput findProductInput);
//
//  @Query("select p " +
//      "from Product p " +
//      "where (:#{#findProductInput.idCategory} is null  or p.category.id = :#{#findProductInput.idCategory}) " +
//      "and (:#{#findProductInput.name} is null  or p.name like %:#{#findProductInput.name}%) " +
//      "and (:#{#findProductInput.price} is null  or p.price between (:#{#findProductInput.price} - 50.0) and " +
//      "(:#{#findProductInput.price} + 50.0)) " +
//      "and (:#{#findProductInput.stock} is null  or p.stock between (:#{#findProductInput.stock} - 5) and " +
//      "(:#{#findProductInput.stock} + 5))" +
//      "and (:#{#findProductInput.brand} is null  or p.brand like %:#{#findProductInput.brand}%)")
//  List<Product> searchProduct(FindProductInput findProductInput,
//                        PageRequest pageRequest,
//                        Sort sort);

  @Query("select p from Product p")
  List<Product> findAll(PageRequest pageRequest);

  @Query("select p from Product p " +
      "where (:category is null or p.category.name like %:category%) " +
      "and (:name is null or p.name like %:name%)")
  List<Product> findByName(String category, String name, PageRequest pageRequest);

}
