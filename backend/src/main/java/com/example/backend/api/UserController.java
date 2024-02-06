package com.example.backend.api;

import com.example.backend.dto.SignUp.SignUpRequest;
import com.example.backend.entity.User;
import com.example.backend.exception.ErrorResponse;
import com.example.backend.exception.InvalidInputException;
import com.example.backend.exception.UserConflictException;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            User response = userService.signUp(signUpRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (UserConflictException e) {
            return new ResponseEntity<>(new ErrorResponse("UserConflict", e.getMessage()), HttpStatus.CONFLICT);
        } catch (InvalidInputException e) {
            return new ResponseEntity<>(new ErrorResponse("InvalidInput", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // 기타 사용자 관련 API들...
}
