// CafeInfoRepository.java
package com.example.backend.repository;

import com.example.backend.entity.CafeInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeInfoRepository extends JpaRepository<CafeInfoEntity, Long> {
    List<CafeInfoEntity> findByCafeNameContainingIgnoreCase(String keyword);
    List<CafeInfoEntity> findByHashtagContainingIgnoreCase(String hashtag);
}
