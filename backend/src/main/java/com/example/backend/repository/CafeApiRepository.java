package com.example.backend.repository;

import com.example.backend.dto.CafeApi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeApiRepository extends JpaRepository<CafeApi, Long> {
    // ID로 정렬된 모든 데이터 조회
    List<CafeApi> findAllByOrderById();
    // 추가적인 쿼리 메서드가 필요하다면 여기에 추가 가능
}
