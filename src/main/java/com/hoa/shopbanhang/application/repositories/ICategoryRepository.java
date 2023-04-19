package com.hoa.shopbanhang.application.repositories;

import com.hoa.shopbanhang.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {

  @Query("select c from Category c where c.name like %?1%")
  Category findByName(String name);

}
