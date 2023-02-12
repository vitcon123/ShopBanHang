package com.hoa.shopbanhang.application.repositories;

import com.hoa.shopbanhang.domain.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITokenRepository extends JpaRepository<Token, Long> {

  @Query("select t from Token t where t.token = ?1")
  Optional<Token> findByToken(String token);
}
