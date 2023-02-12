package com.hoa.shopbanhang.application.repositories;

import com.hoa.shopbanhang.domain.entities.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMediaRepository extends JpaRepository<Media, Long> {
}
