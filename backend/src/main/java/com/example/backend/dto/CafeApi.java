package com.example.backend.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class CafeApi {

    @Id
    private String id;
    private String cafeName;
    private String address;
    private String phone;
    private String kakaoUrl;
    private String latitude;
    private String longitude;
}


