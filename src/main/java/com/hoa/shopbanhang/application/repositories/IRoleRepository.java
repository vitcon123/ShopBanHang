package com.hoa.shopbanhang.application.repositories;

import com.hoa.shopbanhang.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

  @Query("select r from Role r where r.name = ?1")
  Role findByName(String role);
}
