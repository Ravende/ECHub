package com.example.backend.controller;

import com.example.backend.dto.RegistrationRequest;
import com.example.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    // 회원가입 페이지 매핑
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        userService.registerUser(registrationRequest);
        return ResponseEntity.ok("User registered successfully");
    }

    // 로그인 페이지 매핑
    @GetMapping("/login")
    public String login() {
        return "login"; // 로그인 페이지 템플릿 이름 반환 (src/main/resources/templates에 login.html 추가)
    }

}