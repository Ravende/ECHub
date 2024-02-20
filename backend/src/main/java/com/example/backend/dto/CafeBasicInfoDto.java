package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CafeBasicInfoDto {
    private Long cafeId;
    private String cafeName;
    private String address;
    private String phone;
    private List<Long> hashtagId;
    private List<String> hashtag;
    private String businessStatus;
    private List<Long> businessHourId;
    private Map<String, String> businessHour = new HashMap<>();
    private String imageUrl;
    private String latitude;
    private String longitude;
}