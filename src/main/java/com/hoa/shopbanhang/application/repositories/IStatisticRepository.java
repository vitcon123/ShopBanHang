package com.hoa.shopbanhang.application.repositories;

import com.hoa.shopbanhang.domain.entities.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStatisticRepository extends JpaRepository<Statistic, Long> {
}
