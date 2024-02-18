package com.example.backend.controller;

import com.example.backend.dto.CafeBasicInfoDto;
import com.example.backend.dto.CafeDetailInfoDto;
import com.example.backend.dto.ReviewDto;
import com.example.backend.entity.UserEntity;
import com.example.backend.security.JwtUtil;
import com.example.backend.service.CafeService;
import com.example.backend.service.ReviewService;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cafe")
public class CafeController {

    private final CafeService cafeService;
    private final ReviewService reviewService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public CafeController(CafeService cafeService, ReviewService reviewService, UserService userService, JwtUtil jwtUtil) {
        this.cafeService = cafeService;
        this.reviewService = reviewService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // DB 생성
    @GetMapping("/saveFromKakaoApi")
    public ResponseEntity<String> saveCafesFromKakaoApi() {
        cafeService.saveCafesFromKakaoApi();
        return ResponseEntity.status(HttpStatus.OK).body("DB에 성공적으로 등록되었습니다.");
    }

    // 전체 카페 조회
    @GetMapping
    public ResponseEntity<List<CafeBasicInfoDto>> getAllCafesBasicInfo() {
        List<CafeBasicInfoDto> cafesBasicInfo = cafeService.getAllCafesBasicInfo();
        return new ResponseEntity<>(cafesBasicInfo, HttpStatus.OK);
    }

    // 상세 정보 조회
    @GetMapping("/{cafeId}")
    public ResponseEntity<CafeDetailInfoDto> getCafeDetailInfo(@PathVariable Long cafeId) {
        CafeDetailInfoDto cafeDetailInfo = cafeService.getCafeDetailInfo(cafeId);
        if (cafeDetailInfo != null) {
            return new ResponseEntity<>(cafeDetailInfo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 키워드 검색
    @GetMapping("/search")
    public ResponseEntity<List<CafeBasicInfoDto>> searchCafesByKeyword(@RequestParam("q") String keyword) {
        List<CafeBasicInfoDto> cafesBasicInfo = cafeService.searchCafesByKeyword(keyword);
        return new ResponseEntity<>(cafesBasicInfo, HttpStatus.OK);
    }

    // 해시태그 검색
    @GetMapping("/searchByTag")
    public ResponseEntity<List<CafeBasicInfoDto>> searchCafesByHashtag(@RequestParam("q") Long hashtagId) {
        List<CafeBasicInfoDto> cafesBasicInfo = cafeService.searchCafesByHashtag(hashtagId);
        return new ResponseEntity<>(cafesBasicInfo, HttpStatus.OK);
    }

    // 리뷰 작성 기능
    @PostMapping("/{cafeId}/review")
    public ResponseEntity<String> addReview(@PathVariable Long cafeId, @RequestBody ReviewDto reviewDto, @RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        String jwtToken = authorizationHeader.substring(7);
        if (!jwtUtil.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        String userEmail = jwtUtil.getEmailFromToken(jwtToken);
        UserEntity userEntity = userService.findByEmail(userEmail);
        if (userEntity == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        Long reviewId = reviewService.addReview(cafeId, reviewDto, userEntity);
        if (reviewId != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("리뷰가 등록되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당하는 카페를 찾을 수 없습니다.");
        }
    }
}