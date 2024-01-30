package com.example.backend.controller;

import com.example.backend.dto.SignUp.SignUpRequest;
import com.example.backend.dto.SignUp.SignUpResponse;
import com.example.backend.exception.ErrorResponse;
import com.example.backend.exception.InvalidInputException;
import com.example.backend.exception.UserConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SignUpController {

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            // 회원가입 로직 수행 (서비스 클래스를 통해 구현)

            // 가입 성공시
            SignUpResponse response = new SignUpResponse(123L, signUpRequest.getUsername(), signUpRequest.getEmail());
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (UserConflictException e) {
            // 이미 등록된 사용자일 경우
            return new ResponseEntity<>(new com.example.backend.exception.ErrorResponse("UserConflict", "이미 등록된 이메일 주소입니다.") {
            }, HttpStatus.CONFLICT);

        } catch (InvalidInputException e) {
            // 입력 값이 올바르지 않을 경우
            return new ResponseEntity<>(new ErrorResponse("InvalidInput", "입력 값이 올바르지 않습니다. 모든 필드를 채워주세요."), HttpStatus.BAD_REQUEST);
        }
    }
}
