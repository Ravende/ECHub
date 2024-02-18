package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CafeDetailInfoDto {
    private Long cafeId;
    private String cafeName;
    private String address;
    private String phone;
    private List<Long> hashtagId;
    private List<String> hashtag;
    private String kakaoUrl;
    private String scale;
    private String memo;
    private String official;
    private String studentDiscount;
    private String bestMenu;
    private String businessStatus;
    private List<Long> businessHourId;
    private Map<String, String> businessHour = new HashMap<>();
    private List<Long> commentId;
    private List<String> comment;
    private String imageUrl;
}
