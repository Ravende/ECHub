package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CafeBasicInfoDto {
    private String cafeName;
    private String address;
    private String phone;
    private String hashtag;
    private String businessStatus;
    private Map<String, String> businessHour;
}
