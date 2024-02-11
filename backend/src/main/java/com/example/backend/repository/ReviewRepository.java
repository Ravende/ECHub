package com.example.backend.repository;

import com.example.backend.entity.CafeEntity;
import com.example.backend.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findByCafe(CafeEntity cafe);
}
