package com.hoa.shopbanhang.application.repositories;

import com.hoa.shopbanhang.application.inputs.product.SearchProductInput;
import com.hoa.shopbanhang.domain.entities.Category;
import com.hoa.shopbanhang.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {

}
