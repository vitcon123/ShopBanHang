package com.vitweb.vitwebapi.application.repositories;

import com.vitweb.vitwebapi.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {

}
