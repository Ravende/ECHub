package com.example.backend.repository;

import com.example.backend.entity.CafeApi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeApiRepository extends JpaRepository<CafeApi, String> {
    // ID로 정렬된 모든 데이터 조회
    List<CafeApi> findAllByOrderById();
}
