package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CafeBasicInfoDto {
    private String cafeName;
    private String openHour;
    private String closeHour;
    private String address;
    private String phone;
    private String hashtag;
}
