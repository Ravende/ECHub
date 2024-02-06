package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="cafe_api")
public class CafeApi {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 자동 생성되는 ID (기본키)

    @Column(name="place_id")
    private String place_id;

    @Column(name="cafe_name")
    private String cafe_name;

    @Column(name="address")
    private String address;

    @Column(name="phone")
    private String phone;

    @Column(name="kakao_url")
    private String kakao_url;

    @Column(name="latitude")
    private String latitude;

    @Column(name="longitude")
    private String longitude;

    public CafeApi(String place_id, String cafe_name, String address, String phone, String kakao_url, String latitude, String longitude) {
        this.place_id = place_id;
        this.cafe_name = cafe_name;
        this.address = address;
        this.phone = phone;
        this.kakao_url = kakao_url;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}