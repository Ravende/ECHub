package com.example.backend.service;

import com.example.backend.dto.CafeApiDto;
import com.example.backend.entity.CafeApiEntity;
import com.example.backend.repository.CafeApiRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class CafeApiService {

    private final CafeApiRepository cafeApiRepository;

    @Autowired
    public CafeApiService(CafeApiRepository cafeApiRepository) {
        this.cafeApiRepository = cafeApiRepository;
    }

    @Transactional
    public void fetchAndSaveAllData() {
        String[] apiUrls = {
                "https://dapi.kakao.com/v2/local/search/category.json?category_group_code=CE7&size=10&sort=accuracy&page=5&rect=126.942065%2C37.562414%2C126.945468%2C37.559716",
                "https://dapi.kakao.com/v2/local/search/category.json?category_group_code=CE7&size=10&sort=accuracy&page=5&rect=126.945468%2C37.559716%2C126.948871%2C37.562414",
                "https://dapi.kakao.com/v2/local/search/category.json?category_group_code=CE7&size=10&sort=accuracy&page=5&rect=126.945468%2C37.559716%2C126.948871%2C37.557018",
                "https://dapi.kakao.com/v2/local/search/category.json?category_group_code=CE7&size=10&sort=accuracy&page=5&rect=126.942065%2C37.557018%2C126.943199%2C37.559716",
                "https://dapi.kakao.com/v2/local/search/category.json?category_group_code=CE7&size=10&sort=accuracy&page=5&rect=126.943199%2C37.557018%2C126.944334%2C37.559716",
                "https://dapi.kakao.com/v2/local/search/category.json?category_group_code=CE7&size=10&sort=accuracy&page=5&rect=126.944334%2C37.557018%2C126.945468%2C37.559716"
        };

        for (String apiUrl : apiUrls) {
            // API 호출 및 응답 데이터 얻기
            String responseData = fetchApiData(apiUrl);
            // 응답 데이터를 DB에 저장
            if (responseData != null) {
                saveCafeApi(responseData);
            }
        }
    }

    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK 0925dedbdb8f181f0b111f58cf51eab7");
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private String fetchApiData(String apiUrl) {
        HttpHeaders headers = createHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
        } catch (Exception e) {
            System.out.println("API 응답하지 않음");
            return null;
        }

        String responseData = response.getBody();
        if (responseData == null || responseData.isEmpty()) {
            System.out.println("API 응답 데이터가 비어 있음");
            return null;
        }

        System.out.println("API 응답 데이터: " + responseData); // API 응답 출력

        return responseData;
    }

    private void saveCafeApi(String responseData) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(responseData);
            JsonNode documentsNode = rootNode.path("documents");

            for (JsonNode documentNode : documentsNode) {
                String placeId = documentNode.path("id").asText();
                String cafeName = documentNode.path("place_name").asText();
                String address = documentNode.path("road_address_name").asText();
                String phone = documentNode.path("phone").asText();
                String kakaoUrl = documentNode.path("place_url").asText();
                String latitude = documentNode.path("y").asText();
                String longitude = documentNode.path("x").asText();

                // CafeApiDto 객체 생성하여 저장
                CafeApiDto cafeApiDto = new CafeApiDto();
                cafeApiDto.setPlaceId(placeId);
                cafeApiDto.setCafeName(cafeName);
                cafeApiDto.setRoadAddressName(address);
                cafeApiDto.setPhone(phone);
                cafeApiDto.setPlaceUrl(kakaoUrl);
                cafeApiDto.setLatitude(latitude);
                cafeApiDto.setLongitude(longitude);

                // DTO 객체를 Entity로 변환하여 저장
                CafeApiEntity cafeApiEntity = new CafeApiEntity();
                cafeApiEntity.setPlaceId(cafeApiDto.getPlaceId());
                cafeApiEntity.setCafeName(cafeApiDto.getCafeName());
                cafeApiEntity.setAddress(cafeApiDto.getRoadAddressName());
                cafeApiEntity.setPhone(cafeApiDto.getPhone());
                cafeApiEntity.setKakaoUrl(cafeApiDto.getPlaceUrl());
                cafeApiEntity.setLatitude(cafeApiDto.getLatitude());
                cafeApiEntity.setLongitude(cafeApiDto.getLongitude());

                cafeApiRepository.save(cafeApiEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
