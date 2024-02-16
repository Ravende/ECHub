package com.example.backend.controller;

import com.example.backend.dto.JwtToken;
import com.example.backend.dto.UserDto;
import com.example.backend.entity.UserEntity;
import com.example.backend.security.JwtUtil;
import com.example.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(errorMessage.toString());
        } else {
            if (userService.isEmailAlreadyExists(userDto.getEmail())) {
                return ResponseEntity.badRequest().body("이미 가입된 이메일입니다.");
            }

            if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
                return ResponseEntity.badRequest().body("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            }
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

    @PostMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        String jwtToken = authorizationHeader.substring(7);
        if (!jwtUtil.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        String userEmail = jwtUtil.getEmailFromToken(jwtToken);
        UserEntity userEntity = userService.findByEmail(userEmail);
        if (userEntity == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        userService.deleteUser(userEntity);
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }
}