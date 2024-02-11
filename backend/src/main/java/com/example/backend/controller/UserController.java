package com.example.backend.controller;

import com.example.backend.dto.JwtToken;
import com.example.backend.dto.UserDto;
import com.example.backend.security.JwtUtil;
import com.example.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Validated @RequestBody UserDto userDto, org.springframework.validation.BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 실패
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(errorMessage.toString());
        } else {
            // 이메일 중복 확인
            if (userService.isEmailAlreadyExists(userDto.getEmail())) {
                return ResponseEntity.badRequest().body("이미 가입된 이메일입니다.");
            }

            // 비밀번호 확인
            if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
                return ResponseEntity.badRequest().body("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            }

            // 성공
            userService.registerUser(userDto);
            return ResponseEntity.ok("회원가입 성공!");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserDto userDto) {
        if (userService.login(userDto)) {
            String token = jwtUtil.generateToken(userDto.getEmail());
            JwtToken jwtToken = JwtToken.builder()
                    .grantType("Bearer")
                    .accessToken(token)
                    .build();
            return ResponseEntity.ok().body(Map.of("jwtToken", jwtToken, "message", "로그인 성공!"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("일치하는 사용자가 없습니다.");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}