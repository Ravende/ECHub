package com.example.backend.service;

import com.example.backend.dto.CafeApiDto;
import com.example.backend.dto.CafeBasicInfoDto;
import com.example.backend.dto.CafeDetailInfoDto;
import com.example.backend.entity.CafeEntity;
import com.example.backend.repository.CafeRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CafeService {

    private final CafeRepository cafeRepository;

    //여기부터 수정
    @Autowired
    public CafeService(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    public void saveCafesFromKakaoApi() {
        String url = "https://dapi.kakao.com/v2/local/search/category.json";
        String categoryGroupCode = "CE7";
        int size = 15;
        String sort = "accuracy";
        int page = 45;
        List<String> rects = Arrays.asList(
                "126.942065,37.562414,126.945468,37.559716", // 좌상단
                "126.945468,37.559716,126.948871,37.562414", // 우상단
                "126.942065,37.558817,126.943199,37.559716", // 1
                "126.943199,37.558817,126.944334,37.559716", // 2
                "126.944334,37.559716,126.945468,37.558817", // 3
                "126.942065,37.557917,126.943199,37.558817", // 4
                "126.943199,37.558817,126.944334,37.557917", // 5
                "126.944334,37.557917,126.945468,37.558817", // 6
                "126.942065,37.557018,126.943199,37.557917", // 7
                "126.943199,37.557917,126.944334,37.557018", // 8
                "126.944334,37.557018,126.945468,37.557917", // 9
                "126.945468,37.559716,126.946602,37.558817", // a
                "126.946602,37.558817,126.947737,37.559716", // b
                "126.947737,37.559716,126.948871,37.558817", // c
                "126.945468,37.557917,126.946602,37.558817", // d
                "126.946602,37.558817,126.947737,37.557917", // e
                "126.947737,37.557917,126.948871,37.558817", // f
                "126.945468,37.557018,126.946602,37.557018", // g
                "126.946602,37.557018,126.947737,37.557917", // h
                "126.947737,37.557917,126.948871,37.557018"  // i
        );

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK 0925dedbdb8f181f0b111f58cf51eab7"); // REST API 키 설정
        headers.setContentType(MediaType.APPLICATION_JSON);

        for (String rect : rects) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("category_group_code", categoryGroupCode)
                    .queryParam("size", size)
                    .queryParam("sort", sort)
                    .queryParam("page", page)
                    .queryParam("rect", rect);

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    String.class);

            String responseBody = responseEntity.getBody();

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(responseBody);
                JsonNode documents = root.get("documents");

                for (JsonNode document : documents) {
                    CafeApiDto cafeApiDto = objectMapper.treeToValue(document, CafeApiDto.class);
                    CafeEntity cafeEntity = convertToCafeInfoEntity(cafeApiDto);
                    cafeRepository.save(cafeEntity);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private CafeEntity convertToCafeInfoEntity(CafeApiDto cafeApiDto) {
        CafeEntity cafeEntity = new CafeEntity();
        cafeEntity.setCafeName(cafeApiDto.getPlace_name());
        cafeEntity.setAddress(cafeApiDto.getRoad_address_name());
        cafeEntity.setPhone(cafeApiDto.getPhone());
        cafeEntity.setKakaoUrl(cafeApiDto.getPlace_url());
        cafeEntity.setLatitude(cafeApiDto.getY());
        cafeEntity.setLongitude(cafeApiDto.getX());

        return cafeEntity;
    }

    // 전체 카페 기본 정보
    public List<CafeBasicInfoDto> getAllCafesBasicInfo() {
        return cafeRepository.findAll().stream()
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

        return cafeRepository.findByCafeNameContainingIgnoreCase(keyword).stream()
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
        return cafeRepository.findByHashtagContainingIgnoreCase(hashtag).stream()
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
        CafeEntity cafe = cafeRepository.findById(cafeId).orElse(null);
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
            return null; // 해당하는 카페를 찾을 수 없을 때
        }
    }
}