package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CafeDetailInfoDto {
    private String cafeName;
    private String address;
    private String phone;
    private List<String> hashtag;
    private String kakaoUrl;
    private String waiting;
    private String scale;
    private String memo;
    private String official;
    private String studentDiscount;
    private String bestMenu;
    private String businessStatus;
    private Map<String, String> businessHour = new HashMap<>();
    private List<String> comments;
}
