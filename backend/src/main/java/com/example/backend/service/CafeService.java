package com.example.backend.service;

import com.example.backend.dto.CafeApiDto;
import com.example.backend.dto.CafeBasicInfoDto;
import com.example.backend.dto.CafeDetailInfoDto;
import com.example.backend.entity.CafeEntity;
import com.example.backend.entity.HashtagEntity;
import com.example.backend.entity.OpenNowEntity;
import com.example.backend.entity.ReviewEntity;
import com.example.backend.repository.CafeRepository;
import com.example.backend.repository.ReviewRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CafeService {

    private final CafeRepository cafeRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public CafeService(CafeRepository cafeRepository, ReviewRepository reviewRepository) {
        this.cafeRepository = cafeRepository;
        this.reviewRepository = reviewRepository; // ReviewRepository 초기화
    }

    public void saveCafesFromKakaoApi() {
        String url = "https://dapi.kakao.com/v2/local/search/category.json";
        String categoryGroupCode = "CE7";
        int size = 15;
        String sort = "accuracy";
        int page = 45;
        List<String> rects = Arrays.asList(
                "126.942065,37.562414,126.945468,37.559716",
                "126.945468,37.559716,126.948871,37.562414",
                "126.942065,37.558817,126.943199,37.559716",
                "126.943199,37.558817,126.944334,37.559716",
                "126.944334,37.559716,126.945468,37.558817",
                "126.942065,37.557917,126.943199,37.558817",
                "126.943199,37.558817,126.944334,37.557917",
                "126.944334,37.557917,126.945468,37.558817",
                "126.942065,37.557018,126.943199,37.557917",
                "126.943199,37.557917,126.944334,37.557018",
                "126.944334,37.557018,126.945468,37.557917",
                "126.945468,37.559716,126.946602,37.558817",
                "126.946602,37.558817,126.947737,37.559716",
                "126.947737,37.559716,126.948871,37.558817",
                "126.945468,37.557917,126.946602,37.558817",
                "126.946602,37.558817,126.947737,37.557917",
                "126.947737,37.557917,126.948871,37.558817",
                "126.945468,37.557018,126.946602,37.557018",
                "126.946602,37.557018,126.947737,37.557917",
                "126.947737,37.557917,126.948871,37.557018"
        );

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK 0925dedbdb8f181f0b111f58cf51eab7");
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
                    dto.setCafeId(cafe.getId());
                    dto.setCafeName(cafe.getCafeName());
                    dto.setAddress(cafe.getAddress());
                    dto.setPhone(cafe.getPhone());
                    dto.setHashtag(cafe.getHashtag().stream().map(HashtagEntity::getTagName).collect(Collectors.toList()));
                    dto.setBusinessStatus(getBusinessStatus(cafe));
                    dto.setImageUrl(cafe.getImageUrl());

                    List<Long> businessHourId = new ArrayList<>();
                    Map<String, String> businessHour = new HashMap<>();
                    String businessStatus = "";
                    if (cafe.getOpeningHour() != null && !cafe.getOpeningHour().isEmpty()) {
                        for (OpenNowEntity openNow : cafe.getOpeningHour()) {
                            businessHourId.add(openNow.getId());
                            String dayOfWeek = openNow.getDayOfWeek().toString();
                            String hours = openNow.getOpeningTime() + " - " + openNow.getClosingTime();
                            businessHour.put(dayOfWeek, hours);
                        }
                        businessStatus = getBusinessStatus(cafe);
                    }
                    dto.setBusinessHourId(businessHourId);
                    dto.setBusinessHour(sortBusinessHour(businessHour));
                    dto.setBusinessStatus(businessStatus);

                    List<Long> hashtagId = cafe.getHashtag().stream()
                            .map(HashtagEntity::getId)
                            .collect(Collectors.toList());
                    dto.setHashtagId(hashtagId);

                    return dto;
                })
                .collect(Collectors.toList());
    }

    // 키워드 검색
    public List<CafeBasicInfoDto> searchCafesByKeyword(String keyword) {
        keyword = keyword.toLowerCase(); // 영어 대소문자 구분 X
        return cafeRepository.findByCafeNameContainingIgnoreCase(keyword).stream()
                .map(cafe -> {
                    CafeBasicInfoDto dto = new CafeBasicInfoDto();
                    dto.setCafeId(cafe.getId());
                    dto.setCafeName(cafe.getCafeName());
                    dto.setAddress(cafe.getAddress());
                    dto.setPhone(cafe.getPhone());
                    dto.setHashtag(cafe.getHashtag().stream().map(HashtagEntity::getTagName).collect(Collectors.toList()));
                    dto.setBusinessStatus(getBusinessStatus(cafe));
                    dto.setImageUrl(cafe.getImageUrl());

                    List<Long> businessHourId = new ArrayList<>();
                    Map<String, String> businessHour = new HashMap<>();
                    String businessStatus = "";
                    if (cafe.getOpeningHour() != null && !cafe.getOpeningHour().isEmpty()) {
                        for (OpenNowEntity openNow : cafe.getOpeningHour()) {
                            businessHourId.add(openNow.getId());
                            String dayOfWeek = openNow.getDayOfWeek().toString();
                            String hours = openNow.getOpeningTime() + " - " + openNow.getClosingTime();
                            businessHour.put(dayOfWeek, hours);
                        }
                        businessStatus = getBusinessStatus(cafe);
                    }
                    dto.setBusinessHourId(businessHourId);
                    dto.setBusinessHour(sortBusinessHour(businessHour));
                    dto.setBusinessStatus(businessStatus);

                    List<Long> hashtagId = cafe.getHashtag().stream()
                            .map(HashtagEntity::getId)
                            .collect(Collectors.toList());
                    dto.setHashtagId(hashtagId);

                    return dto;
                })
                .collect(Collectors.toList());
    }

    // 해시태그 검색
    public List<CafeBasicInfoDto> searchCafesByHashtag(Long hashtagId) {
        return cafeRepository.findByHashtagId(hashtagId).stream()
                .map(cafe -> {
                    CafeBasicInfoDto dto = new CafeBasicInfoDto();
                    dto.setCafeId(cafe.getId());
                    dto.setCafeName(cafe.getCafeName());
                    dto.setAddress(cafe.getAddress());
                    dto.setPhone(cafe.getPhone());
                    dto.setHashtag(cafe.getHashtag().stream().map(HashtagEntity::getTagName).collect(Collectors.toList()));
                    dto.setBusinessStatus(getBusinessStatus(cafe));
                    dto.setImageUrl(cafe.getImageUrl());

                    List<Long> businessHourId = new ArrayList<>();
                    Map<String, String> businessHour = new HashMap<>();
                    String businessStatus = "";
                    if (cafe.getOpeningHour() != null && !cafe.getOpeningHour().isEmpty()) {
                        for (OpenNowEntity openNow : cafe.getOpeningHour()) {
                            businessHourId.add(openNow.getId());
                            String dayOfWeek = openNow.getDayOfWeek().toString();
                            String hours = openNow.getOpeningTime() + " - " + openNow.getClosingTime();
                            businessHour.put(dayOfWeek, hours);
                        }
                        businessStatus = getBusinessStatus(cafe);
                    }
                    dto.setBusinessHourId(businessHourId);
                    dto.setBusinessHour(sortBusinessHour(businessHour));
                    dto.setBusinessStatus(businessStatus);

                    List<Long> hashtagIds = cafe.getHashtag().stream()
                            .map(HashtagEntity::getId)
                            .collect(Collectors.toList());
                    dto.setHashtagId(hashtagIds);

                    return dto;
                })
                .collect(Collectors.toList());
    }

    // 상세 정보 조회
    public CafeDetailInfoDto getCafeDetailInfo(Long cafeId) {
        CafeEntity cafe = cafeRepository.findById(cafeId).orElse(null);
        if (cafe != null) {
            CafeDetailInfoDto dto = new CafeDetailInfoDto();
            dto.setCafeId(cafe.getId());
            dto.setCafeName(cafe.getCafeName());
            dto.setAddress(cafe.getAddress());
            dto.setPhone(cafe.getPhone());
            dto.setHashtag(cafe.getHashtag().stream().map(HashtagEntity::getTagName).collect(Collectors.toList()));
            dto.setKakaoUrl(cafe.getKakaoUrl());
            dto.setScale(cafe.getScale());
            dto.setMemo(cafe.getMemo());
            dto.setOfficial(cafe.getOfficial());
            dto.setStudentDiscount(cafe.getStudentDiscount());
            dto.setBestMenu(cafe.getBestMenu());
            dto.setImageUrl(cafe.getImageUrl());

            List<Long> businessHourId = new ArrayList<>();
            Map<String, String> businessHour = new HashMap<>();
            String businessStatus = "";
            if (cafe.getOpeningHour() != null && !cafe.getOpeningHour().isEmpty()) {
                for (OpenNowEntity openNow : cafe.getOpeningHour()) {
                    businessHourId.add(openNow.getId());
                    String dayOfWeek = openNow.getDayOfWeek().toString();
                    String hours = openNow.getOpeningTime() + " - " + openNow.getClosingTime();
                    businessHour.put(dayOfWeek, hours);
                }
                businessStatus = getBusinessStatus(cafe);
            }
            dto.setBusinessHourId(businessHourId);
            dto.setBusinessHour(sortBusinessHour(businessHour));
            dto.setBusinessStatus(businessStatus);

            List<Long> hashtagId = cafe.getHashtag().stream()
                    .map(HashtagEntity::getId)
                    .collect(Collectors.toList());
            dto.setHashtagId(hashtagId);

            List<ReviewEntity> reviews = reviewRepository.findByCafe(cafe);
            List<Long> commentId = new ArrayList<>();
            List<String> comment = new ArrayList<>();
            for (ReviewEntity review : reviews) {
                commentId.add(review.getId());
                comment.add(review.getComment());
            }
            dto.setCommentId(commentId);
            dto.setComment(comment);

            return dto;
        } else {
            return null;
        }
    }

    // 월화수목금토일 순서로 정렬
    private Map<String, String> sortBusinessHour(Map<String, String> businessHour) {
        Map<String, String> sortedBusinessHour = new TreeMap<>(Comparator.comparingInt(this::getDayOfWeekOrder));
        sortedBusinessHour.putAll(businessHour);
        return sortedBusinessHour;
    }

    private int getDayOfWeekOrder(String dayOfWeek) {
        return DayOfWeek.valueOf(dayOfWeek.toUpperCase()).getValue();
    }

    // 영업 상태
    private String getBusinessStatus(CafeEntity cafe) {
        Optional<OpenNowEntity> todayOpenInfo = getTodayOpenInfo(cafe);
        if (todayOpenInfo.isEmpty()) {
            return "";
        }
        OpenNowEntity openNowEntity = todayOpenInfo.get();
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek currentDayOfWeek = now.getDayOfWeek();
        LocalTime currentTime = now.toLocalTime();
        if (currentDayOfWeek.equals(openNowEntity.getDayOfWeek()) &&
                currentTime.isAfter(openNowEntity.getOpeningTime()) &&
                currentTime.isBefore(openNowEntity.getClosingTime())) {
            return "영업 중";
        } else {
            return "영업 종료";
        }
    }

    private Optional<OpenNowEntity> getTodayOpenInfo(CafeEntity cafe) {
        DayOfWeek currentDayOfWeek = LocalDateTime.now().getDayOfWeek();
        List<OpenNowEntity> openNowEntityList = cafe.getOpeningHour();
        if (openNowEntityList == null || openNowEntityList.isEmpty()) {
            return Optional.empty();
        }
        return openNowEntityList.stream()
                .filter(openNowEntity -> openNowEntity.getDayOfWeek().equals(currentDayOfWeek))
                .findFirst();
    }
}