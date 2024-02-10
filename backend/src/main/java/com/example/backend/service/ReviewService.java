package com.example.backend.service;

import com.example.backend.dto.ReviewDto;
import com.example.backend.entity.CafeEntity;
import com.example.backend.entity.ReviewEntity;
import com.example.backend.entity.UserEntity;
import com.example.backend.repository.CafeRepository;
import com.example.backend.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CafeRepository cafeRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, CafeRepository cafeRepository) {
        this.reviewRepository = reviewRepository;
        this.cafeRepository = cafeRepository;
    }

    public Long addReview(Long cafeId, ReviewDto reviewDto, UserEntity userEntity) {

        // 카페 정보 확인
        CafeEntity cafe = cafeRepository.findById(cafeId).orElse(null);
        if (cafe == null) {
            return null; // 해당하는 카페를 찾을 수 없는 경우
        }

        // 리뷰 생성
        ReviewEntity review = new ReviewEntity();
        review.setCafe(cafe);
        review.setComment(reviewDto.getComment());

        // 리뷰 저장
        ReviewEntity savedReview = reviewRepository.save(review);
        return savedReview.getId();
    }
}
