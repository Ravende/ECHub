package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CafeApiDto {
    private String placeId;
    private String cafeName;
    private String roadAddressName;
    private String phone;
    private String placeUrl;
    private String latitude;
    private String longitude;
}
