package com.example.backend.dto.SignUp;
// 회원가입 성공 시, 서버에서 클라이언트로 응답할 데이터를 담는 DTO
public class SignUpResponse {
    private Long userId;
    private String username;
    private String email;

    // 생성자
    public SignUpResponse() {
    }

    public SignUpResponse(Long userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    // Getter 및 Setter 메서드
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
