package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cafe")
public class CafeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cafe_name")
    private String cafeName;

    @Column(name = "open_hour")
    private String openHour;

    @Column(name = "close_hour")
    private String closeHour;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "hashtag")
    private String hashtag;

    @Column(name = "kakao_url")
    private String kakaoUrl;

    @Column(name = "waiting")
    private String waiting;

    @Column(name = "scale")
    private String scale;

    @Column(name = "memo")
    private String memo;

    @Column(name = "official")
    private String official;

    @Column(name = "studentDiscount")
    private String studentDiscount;

    @Column(name = "best_menu")
    private String bestMenu;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;
}
