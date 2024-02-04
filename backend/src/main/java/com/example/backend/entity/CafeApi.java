package com.example.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
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