package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OpenNowEntity> openingHours;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @ManyToMany
    @JoinTable(
            name = "cafe_hashtag",
            joinColumns = @JoinColumn(name = "cafe_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private List<HashtagEntity> hashtag;

    @Column(name = "kakao_url")
    private String kakaoUrl;

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

    @Column(name = "image")
    private String imageUrl;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;
}
