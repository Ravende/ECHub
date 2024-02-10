package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter

public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가하는 기본키
    private Long user_id;

    @Column
    private String username;

    @Column(unique = true)
    private String email;

    @Column
    private String password;
}