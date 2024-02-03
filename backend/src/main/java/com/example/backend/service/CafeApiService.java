package com.example.backend.service;

import com.example.backend.dto.CafeApi;
import com.example.backend.repository.CafeApiRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.util.List;


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
            saveCafeApi(responseData);
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
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }

    private void saveCafeApi(String responseData) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(responseData);
            JsonNode documentsNode = rootNode.path("documents");

            for (JsonNode documentNode : documentsNode) {
                CafeApi cafeApi = new CafeApi();
                cafeApi.setId(documentNode.path("id").asText());
                cafeApi.setCafeName(documentNode.path("place_name").asText());
                cafeApi.setAddress(documentNode.path("road_address_name").asText());
                cafeApi.setPhone(documentNode.path("phone").asText());
                cafeApi.setKakaoUrl(documentNode.path("place_url").asText());
                cafeApi.setLatitude(documentNode.path("y").asText());
                cafeApi.setLongitude(documentNode.path("x").asText());

                cafeApiRepository.save(cafeApi);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ID로 정렬된 데이터 조회
    public List<CafeApi> findAllCafes() {
        return cafeApiRepository.findAllByOrderById();
    }
}
