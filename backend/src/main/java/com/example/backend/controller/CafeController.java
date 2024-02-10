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
        this.jwtUtil = jwtUtil; // jwtUtil 초기화
    }

    // DB 생성
    @GetMapping("/saveFromKakaoApi")
    public ResponseEntity<String> saveCafesFromKakaoApi() {
        cafeService.saveCafesFromKakaoApi();
        return ResponseEntity.status(HttpStatus.OK).body("DB에 성공적으로 등록되었습니다.");
    }

    // 전체 카페 리스트 조회
    @GetMapping
    public ResponseEntity<List<CafeBasicInfoDto>> getAllCafesBasicInfo() {
        List<CafeBasicInfoDto> cafesBasicInfo = cafeService.getAllCafesBasicInfo();
        return new ResponseEntity<>(cafesBasicInfo, HttpStatus.OK);
    }

    // 키워드로 검색
    @GetMapping("/search")
    public ResponseEntity<List<CafeBasicInfoDto>> searchCafesByKeyword(@RequestParam("q") String keyword) {
        List<CafeBasicInfoDto> cafesBasicInfo = cafeService.searchCafesByKeyword(keyword);
        return new ResponseEntity<>(cafesBasicInfo, HttpStatus.OK);
    }

    // 해시태그로 검색
    @GetMapping("/searchByTag")
    public ResponseEntity<List<CafeBasicInfoDto>> searchCafesByHashtag(@RequestParam("tag") String hashtag) {
        List<CafeBasicInfoDto> cafesBasicInfo = cafeService.searchCafesByHashtag(hashtag);
        return new ResponseEntity<>(cafesBasicInfo, HttpStatus.OK);
    }

    // 특정 카페 상세 정보 조회
    @GetMapping("/{cafeId}")
    public ResponseEntity<CafeDetailInfoDto> getCafeDetailInfo(@PathVariable Long cafeId) {
        CafeDetailInfoDto cafeDetailInfo = cafeService.getCafeDetailInfo(cafeId);
        if (cafeDetailInfo != null) {
            return new ResponseEntity<>(cafeDetailInfo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 리뷰 작성 기능
    @PostMapping("/{cafeId}/review")
    public ResponseEntity<String> addReview(@PathVariable Long cafeId, @RequestBody ReviewDto reviewDto, @RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다."); // 헤더에 토큰이 없거나 올바르지 않은 형식인 경우
        }

        String jwtToken = authorizationHeader.substring(7); // "Bearer " 이후의 토큰 값 추출

        if (!jwtUtil.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다."); // 유효하지 않은 토큰인 경우
        }

        // 토큰에서 사용자 이메일 추출
        String userEmail = jwtUtil.getEmailFromToken(jwtToken);

        // 사용자 정보 조회
        UserEntity userEntity = userService.findByEmail(userEmail);

        if (userEntity == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다."); // 사용자 정보를 찾을 수 없는 경우
        }

        // 여기서부터 리뷰 작성 기능 추가
        Long reviewId = reviewService.addReview(cafeId, reviewDto, userEntity); // ReviewService 사용
        if (reviewId != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("리뷰가 등록되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당하는 카페를 찾을 수 없습니다."); // 해당하는 카페를 찾을 수 없는 경우
        }
    }
}