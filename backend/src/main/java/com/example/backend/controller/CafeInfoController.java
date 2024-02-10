package com.example.backend.controller;

import com.example.backend.dto.CafeBasicInfoDto;
import com.example.backend.dto.CafeDetailInfoDto;
import com.example.backend.dto.ReviewDto;
import com.example.backend.entity.UserEntity;
import com.example.backend.service.CafeInfoService;
import com.example.backend.service.ReviewService;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cafe")
public class CafeInfoController {

    private final CafeInfoService cafeInfoService;
    private final ReviewService reviewService;
    private final UserService userService; // UserService 필드 추가

    @Autowired
    public CafeInfoController(CafeInfoService cafeInfoService, ReviewService reviewService, UserService userService) {
        this.cafeInfoService = cafeInfoService;
        this.reviewService = reviewService;
        this.userService = userService; // UserService 주입
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

    // 리뷰 작성 기능 추가
    @PostMapping("/{cafeId}/review")
    public ResponseEntity<Long> addReview(@PathVariable Long cafeId, @RequestBody ReviewDto reviewDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 인증되지 않은 사용자
        }

        // 사용자 이름 가져오기
        String username = authentication.getName();
        // 사용자 정보 조회
        UserEntity userEntity = userService.findByUsername(username);

        Long reviewId = reviewService.addReview(cafeId, reviewDto, userEntity); // ReviewService 사용
        if (reviewId != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(reviewId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 해당하는 카페를 찾을 수 없음
        }
    }
}