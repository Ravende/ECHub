package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CafeDetailInfoDto {
    private String cafeName;
    private String openHour;
    private String closeHour;
    private String address;
    private String phone;
    private String hashtag;
    private String kakaoUrl;
    private String waiting;
    private String scale;
    private String memo;
    private String official;
    private String studentDiscount;
    private String bestMenu;
}
