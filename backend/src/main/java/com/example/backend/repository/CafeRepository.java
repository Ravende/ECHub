package com.example.backend.repository;

import com.example.backend.entity.CafeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeRepository extends JpaRepository<CafeEntity, Long> {
    List<CafeEntity> findByCafeNameContainingIgnoreCase(String keyword);
    List<CafeEntity> findByHashtagContainingIgnoreCase(String hashtag);
}
