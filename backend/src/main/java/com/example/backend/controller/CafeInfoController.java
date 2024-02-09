package com.example.backend.controller;

import com.example.backend.dto.CafeBasicInfoDto;
import com.example.backend.dto.CafeDetailInfoDto;
import com.example.backend.service.CafeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cafe")
public class CafeInfoController {

    private final CafeInfoService cafeInfoService;

    @Autowired
    public CafeInfoController(CafeInfoService cafeInfoService) {
        this.cafeInfoService = cafeInfoService;
    }

    // DB 생성
    @GetMapping("/saveFromKakaoApi")
    public ResponseEntity<String> saveCafesFromKakaoApi() {
        cafeInfoService.saveCafesFromKakaoApi();
        return ResponseEntity.status(HttpStatus.OK).body("DB에 성공적으로 등록되었습니다.");
    }

    // 전체 카페 리스트 조회
    @GetMapping
    public ResponseEntity<List<CafeBasicInfoDto>> getAllCafesBasicInfo() {
        List<CafeBasicInfoDto> cafesBasicInfo = cafeInfoService.getAllCafesBasicInfo();
        return new ResponseEntity<>(cafesBasicInfo, HttpStatus.OK);
    }

    // 키워드로 검색
    @GetMapping("/search")
    public ResponseEntity<List<CafeBasicInfoDto>> searchCafesByKeyword(@RequestParam("q") String keyword) {
        List<CafeBasicInfoDto> cafesBasicInfo = cafeInfoService.searchCafesByKeyword(keyword);
        return new ResponseEntity<>(cafesBasicInfo, HttpStatus.OK);
    }

    // 해시태그로 검색
    @GetMapping("/searchByTag")
    public ResponseEntity<List<CafeBasicInfoDto>> searchCafesByHashtag(@RequestParam("tag") String hashtag) {
        List<CafeBasicInfoDto> cafesBasicInfo = cafeInfoService.searchCafesByHashtag(hashtag);
        return new ResponseEntity<>(cafesBasicInfo, HttpStatus.OK);
    }

    // 특정 카페 상세 정보 조회
    @GetMapping("/{cafeId}")
    public ResponseEntity<CafeDetailInfoDto> getCafeDetailInfo(@PathVariable Long cafeId) {
        CafeDetailInfoDto cafeDetailInfo = cafeInfoService.getCafeDetailInfo(cafeId);
        if (cafeDetailInfo != null) {
            return new ResponseEntity<>(cafeDetailInfo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}