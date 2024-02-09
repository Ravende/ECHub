package com.example.backend.service;

import com.example.backend.dto.UserDto;
import com.example.backend.entity.UserEntity;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 (사용자 등록)
    public void registerUser(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(userEntity);
    }

    // 로그인 인증
    public boolean login(UserDto userDto) {
        UserEntity userEntity = userRepository.findByEmail(userDto.getEmail());
        if (userEntity != null && passwordEncoder.matches(userDto.getPassword(), userEntity.getPassword())) {
            return true; // 인증 성공
        }
        return false; // 인증 실패
    }

}
