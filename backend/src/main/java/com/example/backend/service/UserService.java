package com.example.backend.service;

import com.example.backend.dto.SignUp.SignUpRequest;
import com.example.backend.entity.User;
import com.example.backend.exception.UserConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User signUp(SignUpRequest signUpRequest) {
        // 중복 이메일 체크
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new UserConflictException("이미 등록된 이메일 주소입니다.");
        }

        // 비밀번호 해싱
        String hashedPassword = passwordEncoder.encode(signUpRequest.getPassword());

        // 사용자 등록
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(hashedPassword);
        // 기타 필요한 필드 설정

        return userRepository.save(user);
    }
}
