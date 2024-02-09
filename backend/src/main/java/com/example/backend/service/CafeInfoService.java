// CafeInfoService.java
package com.example.backend.service;

import com.example.backend.dto.CafeBasicInfoDto;
import com.example.backend.dto.CafeDetailInfoDto;
import com.example.backend.entity.CafeInfoEntity;
import com.example.backend.repository.CafeInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CafeInfoService {

    private final CafeInfoRepository cafeInfoRepository;

    @Autowired
    public CafeInfoService(CafeInfoRepository cafeInfoRepository) {
        this.cafeInfoRepository = cafeInfoRepository;
    }

    // 전체 카페 기본 정보
    public List<CafeBasicInfoDto> getAllCafesBasicInfo() {
        return cafeInfoRepository.findAll().stream()
                .map(cafe -> {
                    CafeBasicInfoDto dto = new CafeBasicInfoDto();
                    dto.setCafeName(cafe.getCafeName());
                    dto.setOpenHour(cafe.getOpenHour());
                    dto.setCloseHour(cafe.getCloseHour());
                    dto.setAddress(cafe.getAddress());
                    dto.setPhone(cafe.getPhone());
                    dto.setHashtag(cafe.getHashtag());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // 키워드로 검색
    public List<CafeBasicInfoDto> searchCafesByKeyword(String keyword) {
        keyword = keyword.toLowerCase(); // 검색 키워드를 소문자로 변환

        return cafeInfoRepository.findByCafeNameContainingIgnoreCase(keyword).stream()
                .map(cafe -> {
                    CafeBasicInfoDto dto = new CafeBasicInfoDto();
                    dto.setCafeName(cafe.getCafeName());
                    dto.setOpenHour(cafe.getOpenHour());
                    dto.setCloseHour(cafe.getCloseHour());
                    dto.setAddress(cafe.getAddress());
                    dto.setPhone(cafe.getPhone());
                    dto.setHashtag(cafe.getHashtag());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // 해시태그로 검색
    public List<CafeBasicInfoDto> searchCafesByHashtag(String hashtag) {
        return cafeInfoRepository.findByHashtagContainingIgnoreCase(hashtag).stream()
                .map(cafe -> {
                    CafeBasicInfoDto dto = new CafeBasicInfoDto();
                    dto.setCafeName(cafe.getCafeName());
                    dto.setOpenHour(cafe.getOpenHour());
                    dto.setCloseHour(cafe.getCloseHour());
                    dto.setAddress(cafe.getAddress());
                    dto.setPhone(cafe.getPhone());
                    dto.setHashtag(cafe.getHashtag());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // 카페의 상세 정보 조회
    public CafeDetailInfoDto getCafeDetailInfo(Long cafeId) {
        CafeInfoEntity cafe = cafeInfoRepository.findById(cafeId).orElse(null);
        if (cafe != null) {
            CafeDetailInfoDto dto = new CafeDetailInfoDto();
            dto.setCafeName(cafe.getCafeName());
            dto.setOpenHour(cafe.getOpenHour());
            dto.setCloseHour(cafe.getCloseHour());
            dto.setAddress(cafe.getAddress());
            dto.setPhone(cafe.getPhone());
            dto.setHashtag(cafe.getHashtag());
            dto.setKakaoUrl(cafe.getKakaoUrl());
            dto.setWaiting(cafe.getWaiting());
            dto.setScale(cafe.getScale());
            dto.setMemo(cafe.getMemo());
            dto.setOfficial(cafe.getOfficial());
            dto.setStudentDiscount(cafe.getStudentDiscount());
            dto.setBestMenu(cafe.getBestMenu());
            return dto;
        } else {
            return null; // 해당하는 카페를 찾을 수 없을 때 null 반환
        }
    }
}
