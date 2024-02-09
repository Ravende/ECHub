package com.example.backend.repository;

import com.example.backend.entity.CafeApiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeApiRepository extends JpaRepository<CafeApiEntity, Long> {
}
